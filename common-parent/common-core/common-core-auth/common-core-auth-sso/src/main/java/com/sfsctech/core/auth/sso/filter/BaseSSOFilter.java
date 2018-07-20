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
                    logger.info("Don't need to intercept the path：" + requestURI);
                    chain.doFilter(request, response);
                    return;
                }
                final String method = request.getMethod();
                if ("OPTIONS".equals(method)) {
                    logger.info("测试环境跨域请求放行");
                    chain.doFilter(request, response);
                    return;
                }
                logger.info("Request path：" + requestURI);
                // JwtToken信息
                CookieHelper helper = CookieHelper.getInstance(request, response);
                if (ssoProperties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                    jt = JwtCookieUtil.getJwtTokenByCookie(helper);
                } else {
                    jt = JwtCookieUtil.getJwtTokenByHeader(request);
                }
                if (null != jt) {
                    RpcResult<JwtToken> result;
                    // Jwt 认证校验
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
                    // 设置Session attribute
                    Map<String, Object> attribute = SingletonUtil.getCacheFactory().get(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                    if (null != attribute) SessionHolder.getSessionInfo().setAttribute(attribute);

                    // 校验成功
                    if (result.isSuccess()) {
                        jt = result.getResult();
                        try {
                            String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
                            Claims claims = JwtUtil.parseJWT(token);
                            generateSesssion(claims, request);
                            // 更新token
                            if (ssoProperties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                                JwtCookieUtil.updateJwtToken(helper, jt);
                            } else {
                                JwtCookieUtil.updateJwtToken(response, jt);
                            }
                            chain.doFilter(request, response);
                            return;
                        } catch (Exception e) {
                            JwtCookieUtil.clearJwtToken(helper);
                            logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                        }
                    }
                    // 校验失败
                    else {
                        logger.warn(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
                    }
                }
            } catch (Exception e) {
                logger.error(ThrowableUtil.getRootMessage(e), e);
            } finally {
                // 更新Session attribute
                if (null != jt && MapUtil.isNotEmpty(SessionHolder.getSessionInfo().getAttribute())) {
                    SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt(), SessionHolder.getSessionInfo().getAttribute(), SingletonUtil.getAuthConfig().getExpiration());
                }
            }
            String redirect_url = null;
            // 后端架构系统session失效处理
            if (SSOProperties.ItemType.BackendSystem.equals(ssoProperties.getAuth().getItemType())) {
                redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, ssoProperties.getDomain() + requestURI);
            }
            // 非后端架构系统，回调页面处理
            else {
                String form_url = request.getHeader("Referer");
                // 上次路径不为空，并且属于当前系统的页面，并且不是首页
                if (StringUtil.isNotBlank(form_url) && form_url.contains(ssoProperties.getDomain()) && !form_url.contains(ssoProperties.getHomePage())) {
                    // 项目类型是前端系统
                    if (SSOProperties.ItemType.FrontendSystem.equals(ssoProperties.getAuth().getItemType())) {
                        redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, form_url);
                    }
                    // 项目类型是frame服务
                    else if (SSOProperties.ItemType.FrameService.equals(ssoProperties.getAuth().getItemType())) {
                        redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, ssoProperties.getHomePage() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + form_url);
                    }
                }
            }
            // 登录页面拼接系统首页
            if (StringUtil.isBlank(redirect_url)) {
                redirect_url = ssoProperties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, ssoProperties.getHomePage());
            }
            // 登录超时处理
            ResponseUtil.setNoCacheHeaders(response);
            // Ajax请求
            if (HttpUtil.isAjaxRequest(request)) {
                response.getWriter().write("{result:session}");
            } else {
                response.sendRedirect(redirect_url);
            }
        } finally {
            SessionHolder.clear();
        }
    }

    private RpcResult<JwtToken> localVerify(JwtToken jt) {
        RpcResult<JwtToken> result = SSOUtil.generalVerify(jt, logger);
        if (!result.isSuccess()) {
            return result;
        }// 获取salt_CacheKey
        String salt_CacheKey = result.getAttachs().get("salt_CacheKey").toString();
        // 会话保持剩余时间（秒）
        long loginedTimeStamp = SingletonUtil.getCacheFactory().getCacheClient().ttl(salt_CacheKey + LabelConstants.POUND + jt.getSalt());
        // 如果离超时间还有一半左右，刷新Jwt
        if (SingletonUtil.getJwtProperties().getExpiration() > 0 && (loginedTimeStamp <= (SingletonUtil.getJwtProperties().getExpiration() / 2))) {
            // 解密Jwt
            String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
            // 获取jwt Claims
            Claims claims = JwtUtil.parseJWT(token);
            // 刷新Jwt
            SSOUtil.refreshJwt(claims, String.valueOf(claims.get(SSOConstants.LOGIN_ACCOUNT)), salt_CacheKey, jt, logger);
            result.setResult(jt);
        }
        return result;
    }

    protected abstract SSOCheckInterface getcheck();

    protected abstract void generateSesssion(Claims claims, HttpServletRequest request);
}
