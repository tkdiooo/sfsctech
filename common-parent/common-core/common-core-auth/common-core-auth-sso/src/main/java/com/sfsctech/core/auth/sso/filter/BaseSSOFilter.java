package com.sfsctech.core.auth.sso.filter;

import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.inf.SSOAuthorizationInterface;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.auth.sso.util.SessionKeepUtil;
import com.sfsctech.core.auth.sso.util.SingletonUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.SessionHolder;
import com.sfsctech.core.base.session.SessionInfo;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.*;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class BaseSSOFilter
 *
 * @author 张麒 2018-7-11.
 * @version Description:
 */
public abstract class BaseSSOFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(BaseSSOFilter.class);

    @Autowired
    private SSOProperties properties;

    @Autowired
    private SSOAuthorizationInterface authorizationInterface;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        CookieHelper helper = CookieHelper.getInstance(request, response);
        // SessionInfo
        SessionInfo session = new SessionInfo();
        SessionHolder.setSessionInfo(session);
        try {
            // 请求路径
            String requestURI = request.getRequestURI();
            logger.info("请求路径:{}", requestURI);
            try {
                String token;
                final String method = request.getMethod();
                if (SingletonUtil.getApplication().getActive().equals("dev") && "OPTIONS".equals(method)) {
                    logger.info("开发环境跨域请求放行");
                    chain.doFilter(request, response);
                    return;
                }
                logger.info("Session保持类型:{}", properties.getAuth().getSessionKeep());
                // JwtToken信息
                if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                    token = SessionKeepUtil.getCertificateByCookie(helper);
                } else {
                    token = SessionKeepUtil.getCertificateByHeader(request);
                }
                logger.info("获取JwtToken:{}", token);
                if (StringUtil.isNotBlank(token)) {
                    RpcResult<JwtToken> result;
                    // Jwt 认证校验
                    logger.info("Session类型:{}", properties.getAuth().getSessionType());
                    if (properties.getAuth().getSessionType().equals(SSOProperties.SessionType.Token)) {
                        result = authorizationInterface.tokenVerify(token);
                    } else if (properties.getAuth().getSessionType().equals(SSOProperties.SessionType.Jwt)) {
                        result = jwtVerify(token);
                    } else {
                        result = new RpcResult<>();
                        result.setSuccess(false);
                        result.setMessage("校验规则无法匹配");
                    }
                    logger.info("用户Session校验结果:{}", result);
                    // 校验成功
                    if (result.isSuccess()) {
                        try {
                            JwtToken jt = result.getResult();
                            Claims claims = JwtUtil.parseJWT(jt.getJwt());
                            logger.info("调用自定义方法:customSession");
                            customSession(claims, request);
                            // 更新token
                            logger.info("更新Jwt,更新策略:{}", properties.getAuth().getSessionKeep());
                            if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                                SessionKeepUtil.updateCertificate(helper, jt.getCertificate());
                            } else {
                                SessionKeepUtil.updateCertificate(response, jt.getCertificate());
                            }
                        } catch (Exception e) {
                            logger.warn("Jwt处理异常:清理Jwt");
                            SessionKeepUtil.clearCertificate(helper);
                            logger.warn("异常信息:{}", ListUtil.toString(result.getMessages(), LabelConstants.COMMA), e);
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                }
            } catch (Exception e) {
                logger.error("异常:{}", ThrowableUtil.getRootMessage(e), e);
            }
            logger.info("用户Session失效");
            // 项目类型是单页应用
            if (SSOProperties.AppType.RestAPI.equals(properties.getAuth().getAppType())) {
                response.getWriter().write(LabelConstants.OPEN_CURLY_BRACE + LabelConstants.QUOTE + "result" + LabelConstants.QUOTE + LabelConstants.COLON + LabelConstants.QUOTE + "session" + LabelConstants.QUOTE + LabelConstants.CLOSE_CURLY_BRACE);
                return;
            }

            // TODO
            String redirect_url = null;
            // 后端模板应用session失效处理
            if (SSOProperties.AppType.Backend.equals(properties.getAuth().getAppType())) {
                redirect_url = properties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, properties.getDomain() + requestURI);
            }
            // 后端模板应用，回调页面处理
            else {
                String form_url = request.getHeader("Referer");
                // 上次路径不为空，并且属于当前系统的页面，并且不是首页
                if (StringUtil.isNotBlank(form_url) && form_url.contains(properties.getDomain()) && !form_url.contains(properties.getHomePage())) {
                    // 项目类型是前端系统
                    if (SSOProperties.AppType.Frontend.equals(properties.getAuth().getAppType())) {
                        redirect_url = properties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, form_url);
                    }
                    // 项目类型是模板应用
                    else if (SSOProperties.AppType.Template.equals(properties.getAuth().getAppType())) {
                        redirect_url = properties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, properties.getHomePage() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + form_url);
                    }
                }
            }
            // 登录页面拼接系统首页
            if (StringUtil.isBlank(redirect_url)) {
                redirect_url = properties.getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, properties.getHomePage());
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

    protected abstract SSOAuthorizationInterface getcheck();

    protected abstract void customSession(Claims claims, HttpServletRequest request);

    protected abstract RpcResult<JwtToken> jwtVerify(String token);

}
