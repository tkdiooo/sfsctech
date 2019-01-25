package com.sfsctech.core.auth.base.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;

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
    private PortMapper portMapper = new PortMapperImpl();
    private PortResolver portResolver = new PortResolverImpl();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (super.isUseForward()) {
            super.commence(request, response, authException);
        }
        redirectStrategy.sendRedirect(request, response, this.buildRedirectUrlToLoginPage(request, response, authException));
    }

    @Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String loginForm = this.determineUrlToUseForThisRequest(request, response, authException);
        if (UrlUtils.isAbsoluteUrl(loginForm)) {
            return loginForm;
        } else {
            int serverPort = this.portResolver.getServerPort(request);
            String scheme = request.getScheme();
            RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
            urlBuilder.setScheme(scheme);
            urlBuilder.setServerName(request.getServerName());
            urlBuilder.setPort(serverPort);
            urlBuilder.setContextPath(request.getContextPath());
            urlBuilder.setPathInfo(loginForm);
            if (super.isForceHttps() && "http".equals(scheme)) {
                Integer httpsPort = this.portMapper.lookupHttpsPort(serverPort);
                if (httpsPort != null) {
                    urlBuilder.setScheme("https");
                    urlBuilder.setPort(httpsPort);
                } else {
                    logger.warn("Unable to redirect to HTTPS as no port mapping found for HTTP port " + serverPort);
                }
            }

            return urlBuilder.getUrl();
        }
    }
}
