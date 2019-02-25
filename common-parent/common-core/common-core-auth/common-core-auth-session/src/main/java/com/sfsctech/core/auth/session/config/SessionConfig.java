package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.auth.base.config.SkipPathConfig;
import com.sfsctech.core.auth.session.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import(SkipPathConfig.class)
public class SessionConfig extends AuthSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.authConfigure(http)) {
            // session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(config.getWelcomeFile());
    }
}
