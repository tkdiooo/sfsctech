package com.sfsctech.core.auth.session.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class LoginSuccessHandler
 *
 * @author 张麒 2019-1-24.
 * @version Description:
 */
public class LoginSuccessHandler extends BaseSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private String successUrl;

    public LoginSuccessHandler(String successUrl) {
        this.successUrl = successUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        init(request, authentication);
        // 通过权限定义登录成功跳转路径
//        if (authentication.getAuthorities().size() > 0) {
//            authentication.getAuthorities().forEach(authority -> {
//                if (authority.getAuthority().equals("ROLE_USER")) {
//                    try {
//                        redirectStrategy.sendRedirect(request, response, "/user");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
//                    try {
//                        redirectStrategy.sendRedirect(request, response, "/admin");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    throw new IllegalStateException();
//                }
//            });
//        }
        super.transfer(request, response, this.successUrl);
    }
}
