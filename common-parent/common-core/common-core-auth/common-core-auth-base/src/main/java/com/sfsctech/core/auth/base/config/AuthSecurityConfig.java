package com.sfsctech.core.auth.base.config;

import com.sfsctech.core.auth.base.handler.AuthenticationFailureHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Class LoginSecurityConfig
 *
 * @author 张麒 2019-2-22.
 * @version Description:
 */
public abstract class AuthSecurityConfig extends BaseWebSecurityConfig {

    protected boolean authConfigure(HttpSecurity http) throws Exception {
        if (super.basicConfigure(http)) {
            // 自定义登录
            FormLoginConfigurer<HttpSecurity> formLogin = http.formLogin();
            // 自定义登录成功处理
            if (null != config.auth().getLogin().getAuthenticationSuccessHandler()) {
                formLogin.successHandler(config.auth().getLogin().getAuthenticationSuccessHandler().newInstance());
            }
            // 默认登录成功处理
            else {
                formLogin.successHandler(authenticationSuccessHandler());
            }
            // 自定义登录失败处理
            if (null != config.auth().getLogin().getAuthenticationFailureHandler()) {
                formLogin.failureHandler(config.auth().getLogin().getAuthenticationFailureHandler().newInstance());
            }
            // 默认登录失败处理
            else {
                formLogin.failureHandler(new AuthenticationFailureHandler(config.getWelcomeFile()));
            }
            // 自定义登出页面
            http.logout().logoutUrl(config.auth().getLogout().getUrl())
                    // 自定义登出成功
                    .logoutSuccessHandler(logoutSuccessHandler());
            return true;
        }
        return false;
    }

    protected abstract AuthenticationSuccessHandler authenticationSuccessHandler();

    protected abstract LogoutSuccessHandler logoutSuccessHandler();
}
