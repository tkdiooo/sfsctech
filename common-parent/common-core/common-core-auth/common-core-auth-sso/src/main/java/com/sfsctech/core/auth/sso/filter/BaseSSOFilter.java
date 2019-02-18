package com.sfsctech.core.auth.sso.filter;

import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.inf.SSOCheckInterface;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.util.JwtCookieUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.auth.sso.util.SSOUtil;
import com.sfsctech.core.auth.sso.util.SingletonUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.filter.BaseFilter;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.SessionHolder;
import com.sfsctech.core.base.session.SessionInfo;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.*;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Class BaseSSOFilter
 *
 * @author 张麒 2018-7-11.
 * @version Description:
 */
public abstract class BaseSSOFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException {
        // 生成SessionInfo
        SessionInfo session = new SessionInfo();
        SessionHolder.setSessionInfo(session);
        SSOProperties ssoProperties = SingletonUtil.getSSOProperties();
        try {
            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            // 请求路径
            String requestURI = request.getRequestURI();
            JwtToken jt = null;
            try {
                // 请求路径是无需校验的路径
                if (FilterHandler.isExclusion(requestURI, super.excludesPattern)) {
                    logger.info("不需要拦截的路径:{}", requestURI);
                    chain.doFilter(request, response);
                    return;
                }
                final String method = request.getMethod();
                if (SingletonUtil.getApplication().getActive().equals("dev") && "OPTIONS".equals(method)) {
                    logger.info("开发环境跨域请求放行");
                    chain.doFilter(request, response);
                    return;
                }
                logger.info("请求路径:{}", requestURI);
                // JwtToken信息
                CookieHelper helper = CookieHelper.getInstance(request, response);
                if (ssoProperties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                    jt = JwtCookieUtil.getJwtTokenByCookie(helper);
                } else {
                    jt = JwtCookieUtil.getJwtTokenByHeader(request);
                }
                logger.info("获取JwtToken:{}", jt);
                if (null != jt) {
                    RpcResult<JwtToken> result;
                    // Jwt 认证校验
                    logger.info("Jwt校验方式:{}", ssoProperties.getAuth().getWay());
                    SSOCheckInterface check = getcheck();
                    if (ssoProperties.getAuth().getWay().equals(SSOProperties.AuthWay.Complex)) {
                        result = check.complexVerify(jt);
                    } else if (ssoProperties.getAuth().getWay().equals(SSOProperties.AuthWay.Simple)) {
                        result = check.simpleVerify(jt);
                    } else if (ssoProperties.getAuth().getWay().equals(SSOProperties.AuthWay.Local)) {
                        result = localVerify(jt);
                    } else {
                        result = new RpcResult<>();
                        result.setSuccess(false);
                        result.setMessage("校验规则无法匹配");
                    }
                    // 校验成功
                    if (result.isSuccess()) {
                        logger.info("用户Session校验成功:{}", result.isSuccess());

                        // 设置Session attribute
                        logger.info("根据缓存key:{}，获取用户Session缓存属性", jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                        Map<String, Object> attribute = SingletonUtil.getCacheFactory().get(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                        logger.info("用户Session缓存属性数量:{}", null != attribute ? attribute.size() : null);
                        if (null != attribute) SessionHolder.getSessionInfo().setAttribute(attribute);

                        jt = result.getResult();
                        try {
                            logger.info("Jwt信息:{}", jt.toString());
                            String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
                            logger.info("token信息:{}", token);
                            Claims claims = JwtUtil.parseJWT(token);
                            logger.info("调用自定义方法:generateSesssion");
                            generateSesssion(claims, request);
                            // 更新token
                            logger.info("更新Jwt,更新策略:{}", ssoProperties.getAuth().getSessionKeep());
                            if (ssoProperties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                                JwtCookieUtil.updateJwtToken(helper, jt);
                            } else {
                                JwtCookieUtil.updateJwtToken(response, jt);
                            }
                        } catch (Exception e) {
                            logger.warn("Jwt处理异常:清理Jwt");
                            JwtCookieUtil.clearJwtToken(helper);
                            logger.warn("异常信息:{}", ListUtil.toString(result.getMessages(), LabelConstants.COMMA), e);
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                    // 校验失败
                    else {
                        logger.warn("校验失败:{}", result);
                    }
                }
            } catch (Exception e) {
                logger.error("异常:{}", ThrowableUtil.getRootMessage(e), e);
            } finally {
                // 更新Session attribute
                if (null != jt && MapUtil.isNotEmpty(SessionHolder.getSessionInfo().getAttribute())) {
                    // TODO 需要验证Session 属性具体内容
                    logger.info("更新Session attribute");
                    SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt(), SessionHolder.getSessionInfo().getAttribute(), SingletonUtil.getAuthConfig().getExpiration());
                }
            }
            logger.info("用户Session失效");
            // 项目类型是单页应用
            if (SSOProperties.AppType.RestAPI.equals(ssoProperties.getAuth().getAppType())) {
                response.getWriter().write(LabelConstants.OPEN_CURLY_BRACE + LabelConstants.QUOTE + "result" + LabelConstants.QUOTE + LabelConstants.COLON + LabelConstants.QUOTE + "session" + LabelConstants.QUOTE + LabelConstants.CLOSE_CURLY_BRACE);
                return;
            }
            // TODO
            String redirect_url = null;
            // 后端模板应用session失效处理
            if (SSOProperties.AppType.Backend.equals(ssoProperties.getAuth().getAppType())) {
                redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, ssoProperties.getDomain() + requestURI);
            }
            // 后端模板应用，回调页面处理
            else {
                String form_url = request.getHeader("Referer");
                // 上次路径不为空，并且属于当前系统的页面，并且不是首页
                if (StringUtil.isNotBlank(form_url) && form_url.contains(ssoProperties.getDomain()) && !form_url.contains(ssoProperties.getHomePage())) {
                    // 项目类型是前端系统
                    if (SSOProperties.AppType.Frontend.equals(ssoProperties.getAuth().getAppType())) {
                        redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, form_url);
                    }
                    // 项目类型是模板应用
                    else if (SSOProperties.AppType.Template.equals(ssoProperties.getAuth().getAppType())) {
                        redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, ssoProperties.getHomePage() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + form_url);
                    }
                }
            }
            // 登录页面拼接系统首页
            if (StringUtil.isBlank(redirect_url)) {
                redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, ssoProperties.getHomePage());
            }
            // 登录超时处理
            ResponseUtil.setNoCacheHeaders(response);
            // Ajax请求
            if (HttpUtil.isAjaxRequest(request)) {
                response.getWriter().write("<script>window.parent.location.href='" + redirect_url + "';</script>");
            } else {
                response.sendRedirect(redirect_url);
            }
        } finally {
            SessionHolder.clear();
        }
    }


    protected abstract SSOCheckInterface getcheck();

    protected abstract void generateSesssion(Claims claims, HttpServletRequest request);

    protected abstract RpcResult<JwtToken> localVerify(JwtToken jt);
}
