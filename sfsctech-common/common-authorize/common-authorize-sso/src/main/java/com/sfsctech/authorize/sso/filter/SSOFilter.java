package com.sfsctech.authorize.sso.filter;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.sfsctech.authorize.base.util.CacheKeyUtil;
import com.sfsctech.authorize.sso.util.JwtCookieUtil;
import com.sfsctech.authorize.base.util.JwtUtil;
import com.sfsctech.authorize.sso.util.SingletonUtil;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.base.session.SessionInfo;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.*;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.authorize.sso.properties.SSOProperties;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.rpc.util.RpcUtil;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseFilter {

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
                    // 设置Session attribute
                    Map<String, Object> attribute = SingletonUtil.getCacheFactory().get(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt());
                    if (null != attribute) SessionHolder.getSessionInfo().setAttribute(attribute);
                    // Jwt 认证校验
                    ActionResult<JwtToken> result;
                    // 通过http校验
                    if (ssoProperties.getAuth().getProtocol().equals(SSOProperties.AuthProtocol.Http)) {
                        String responseContent = SingletonUtil.getRestTemplate().getForObject(ssoProperties.getCheckUrl() + LabelConstants.QUESTION + SSOConstants.UAMS_CHECK_PARAM_NAME + LabelConstants.EQUAL + JSON.toJSONString(jt), String.class);
                        result = RpcUtil.parseActionResult(responseContent, RpcConstants.Status.Successful, new TypeToken<ActionResult<JwtToken>>() {
                        }.getType());
                    }
                    // 通过dubbo rpc 校验
                    else {
                        if (ssoProperties.getAuth().getWay().equals(SSOProperties.AuthWay.Complex)) {
                            result = SingletonUtil.getVerifyService().complexVerify(jt);
                        } else {
                            result = SingletonUtil.getVerifyService().simpleVerify(jt);
                        }
                    }
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
                        } catch (BizException e) {
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
                if (ExcludesConstants.isExclusion(requestURI, excludesPattern)) {
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
}
