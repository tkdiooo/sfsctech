package com.sfsctech.core.auth.sso.filter;

import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.util.CacheKeyUtil;
import com.sfsctech.core.auth.sso.util.JwtCookieUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
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
            logger.info("Request path：" + requestURI);
            // JwtToken信息
            CookieHelper helper = CookieHelper.getInstance(request, response);
            JwtToken jt = null;
            try {
                jt = JwtCookieUtil.getJwtTokenByCookie(helper);
                if (null != jt) {
                    // Jwt 认证校验
                    RpcResult<JwtToken> result = check(jt, ssoProperties.getAuth().getWay());
                    // 设置Session attribute
                    Map<String, Object> attribute = SingletonUtil.getCacheFactory().get(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                    if (null != attribute) SessionHolder.getSessionInfo().setAttribute(attribute);

                    // 校验成功
                    if (result.isSuccess()) {
                        jt = result.getResult();
                        String token = EncrypterTool.decrypt(jt.getJwt(), jt.getSalt());
                        try {
                            Claims claims = JwtUtil.parseJWT(token);
                            // 设置UserAuthData
                            SessionHolder.getSessionInfo().setUserAuthData(CacheKeyUtil.getUserAuthData(claims));
                            // 设置RoleInfo

                            // 更新token
                            JwtCookieUtil.updateJwtToken(helper, jt);
                            // 设置session
                            request.getSession().setAttribute(SSOConstants.CONST_UAMS_ASSERTION, SessionHolder.getSessionInfo().getUserAuthData());
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
                // 请求路径是无需校验的路径
                if (FilterHandler.isExclusion(requestURI, excludesPattern)) {
                    logger.info("Don't need to intercept the path：" + requestURI);
                    chain.doFilter(request, response);
                    return;
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
                response.getWriter().write("<script>window.parent.location.href='" + redirect_url + "';</script>");
            } else {
                response.sendRedirect(redirect_url);
            }
        } finally {
            SessionHolder.clear();
        }
    }

    protected abstract RpcResult<JwtToken> check(JwtToken jt, SSOProperties.AuthWay authWay);
}
