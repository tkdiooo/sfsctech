package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.core.auth.session.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
public class SessionConfig extends AuthSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.authConfigure(http)) {
            // session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        if (authProperties.getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
            return new CookieHttpSessionIdResolver();
        } else {
            return HeaderHttpSessionIdResolver.xAuthToken();
        }
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(config.getWelcomeFile(), authProperties.getSessionKeep());
    }
}
