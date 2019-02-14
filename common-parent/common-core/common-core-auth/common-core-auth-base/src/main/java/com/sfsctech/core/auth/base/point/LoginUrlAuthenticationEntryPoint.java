package com.sfsctech.core.auth.base.point;

import com.sfsctech.support.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class LoginUrlAuthenticationEntryPoint
 *
 * @author 张麒 2019-1-25.
 * @version Description:
 */
public class LoginUrlAuthenticationEntryPoint extends org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(LoginUrlAuthenticationEntryPoint.class);
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.info("Request URL：{}", HttpUtil.getFullUrl(request));
        // forward调整或https协议，由Spring原生处理
        if (super.isUseForward() || !getLoginFormUrl().startsWith("http")) {
            super.commence(request, response, authException);
            return;
        }
        // redirect方式跳转登录页面
        redirectStrategy.sendRedirect(request, response, getLoginFormUrl());
    }

}
