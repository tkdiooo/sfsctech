package com.sfsctech.core.auth.sso.server.config;

import com.sfsctech.core.auth.base.config.SkipPathConfig;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.core.auth.base.sso.constants.SSOConstants;
import com.sfsctech.core.auth.base.sso.properties.JwtProperties;
import com.sfsctech.core.auth.base.sso.properties.SSOProperties;
import com.sfsctech.core.auth.base.sso.token.loader.JwtCookieTokenLoader;
import com.sfsctech.core.auth.base.sso.token.loader.JwtHeaderTokenLoader;
import com.sfsctech.core.auth.base.sso.token.loader.TokenLoader;
import com.sfsctech.core.auth.session.config.AuthSecurityConfig;
import com.sfsctech.core.auth.sso.server.handler.LoginSuccessHandler;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenFactory;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Class AuthProperties
 *
 * @author 张麒 2017-12-4.
 * @version Description:
 */
@Configuration
@Import({SkipPathConfig.class, SSOProperties.class, JwtProperties.class, CacheConfig.class, ApplicationInitialize.class})
public class SSOConfig extends AuthSecurityConfig {

    @Autowired
    private JwtProperties properties;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Autowired
    private ApplicationInitialize applicationInitialize;

    @Bean
    public JwtTokenFactory jwtTokenFactory() {
        return new JwtTokenFactory(properties);
    }

    @Bean
    public TokenLoader tokenLoader() {
        if (authProperties.getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
            return new JwtCookieTokenLoader();
        } else {
            return new JwtHeaderTokenLoader();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.authConfigure(http)) {
            // 无状态session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 禁用缓存
                    .and().headers().cacheControl();
            // jwt通过Cookie保持，登出后销毁Cookie
            if (authProperties.getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
                http.logout().deleteCookies("JSESSIONID" + LabelConstants.COMMA + SSOConstants.TOKEN_IDENTIFY_COOKIE);
            }
        }
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(factory, jwtTokenFactory(), tokenLoader(), config.getWelcomeFile(), applicationInitialize);
    }
}