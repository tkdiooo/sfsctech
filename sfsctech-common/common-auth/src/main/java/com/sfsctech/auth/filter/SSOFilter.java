package com.sfsctech.auth.filter;

import com.sfsctech.auth.util.CacheKeyUtil;
import com.sfsctech.auth.util.JwtCookieUtil;
import com.sfsctech.auth.util.JwtUtil;
import com.sfsctech.auth.util.SingletonUtil;
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
import com.sfsctech.constants.SSOConstants;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.rpc.result.ActionResult;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // 生成SessionInfo
        SessionInfo session = new SessionInfo();
        SessionHolder.setSessionInfo(session);
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
                    if (SingletonUtil.getSSOProperties().getAuth().getWay().equals(SSOProperties.AuthWay.Simple)) {
                        result = SingletonUtil.getVerifyService().simpleVerify(jt);
                    } else {
                        result = SingletonUtil.getVerifyService().complexVerify(jt);
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
                    SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(jt.getSalt_CacheKey() + LabelConstants.DOUBLE_POUND + jt.getSalt(), SessionHolder.getSessionInfo().getAttribute(), SingletonUtil.getAuthProperties().getExpiration());
                }
            }
            String form_url = request.getHeader("Referer");
            // 上次路径不为空，并且不是门户路径，并且项目类型是平台类型
            if (StringUtil.isNotBlank(form_url) && !SingletonUtil.getSSOProperties().getPortalUrl().contains(requestURI) && SSOProperties.ItemType.Platform.equals(SingletonUtil.getSSOProperties().getAuth().getItemType())) {
                form_url = SingletonUtil.getSSOProperties().getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, form_url);
            }
            // 上次路径不为空，并且不是门户路径，并且项目类型是服务类型
            else if (StringUtil.isNotBlank(form_url) && !SingletonUtil.getSSOProperties().getPortalUrl().contains(requestURI) && SSOProperties.ItemType.Server.equals(SingletonUtil.getSSOProperties().getAuth().getItemType())) {
                form_url = SingletonUtil.getSSOProperties().getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, SingletonUtil.getSSOProperties().getPortalUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + form_url);
            }
            // 上次路径为空或者是门户路径
            else {
                form_url = SingletonUtil.getSSOProperties().getLoginUrl() + LabelConstants.QUESTION + SSOConstants.PARAM_FROM_URL + LabelConstants.EQUAL + EncrypterTool.encrypt(EncrypterTool.Security.Des3ECB, SingletonUtil.getSSOProperties().getPortalUrl());
            }
            // 登录超时处理
            ResponseUtil.setNoCacheHeaders(response);
            // Ajax请求
            if (HttpUtil.isAjaxRequest(request)) {
                response.getWriter().write("<script>window.parent.location.href='" + form_url + "';</script>");
            } else {
                response.sendRedirect(form_url);
            }
        } finally {
            SessionHolder.clear();
        }
    }
}
