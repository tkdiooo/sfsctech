package com.sfsctech.dubbox.filter;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.base.session.SessionInfo;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.dubbox.inf.VerifyService;
import com.sfsctech.dubbox.util.JwtUtil;
import com.sfsctech.dubbox.util.JwtTokenUtil;
import com.sfsctech.base.filter.BaseFilter;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.rpc.result.ActionResult;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseFilter {

    @Override
    public void doHandler(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            SessionInfo session = new SessionInfo();
            SessionHolder.setSessionInfo(session);
            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            String requestURI = request.getRequestURI();
            logger.info("Request path：" + requestURI);
            if (ExcludesConstants.isExclusion(requestURI, excludesPattern)) {
                logger.info("Don't need to intercept the path：" + requestURI);
                chain.doFilter(request, response);
                return;
            }
            // SSO用户登录验证
            CookieHelper helper = CookieHelper.getInstance(request, response);
            final JwtToken jt = JwtTokenUtil.getJwtTokenByCookie(helper);
            if (null != jt) {
                VerifyService service = (VerifyService) SpringContextUtil.getBean(ReferenceConfig.class).get();
                // 校验jwt信息
                ActionResult<JwtToken> result = service.check(jt);
                // 校验成功
                if (result.isSuccess()) {
                    Claims claims = JwtUtil.parseJWT(result.getResult().getJwt());
                    // 设置UserAuthInfo
//                    session.setUserAuthInfo(claims.get(AuthConstants.JWT_USER_AUTH_INFO));
                    // 设置Session attribute
//                    session.setAttribute(claims.get(AuthConstants.JWT_SESSION_ATTRIBUTE));
                    // 设置RoleInfo
                    // 更新token
                    JwtTokenUtil.updateJwtToken(helper, result.getResult());

                }
                // 校验失败
                else {

                }
            } else {
                chain.doFilter(request, response);
            }
            // 跳转单点登录首页
//            AppConfig appConfig = SpringContextUtil.getBean(AppConfig.class);
        } finally {
            SessionHolder.clear();
        }
    }
}
