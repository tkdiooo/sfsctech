package com.sfsctech.core.auth.sso.server.config;

import com.sfsctech.core.auth.base.config.AuthSecurityConfig;
import com.sfsctech.core.auth.base.config.SkipPathConfig;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.core.auth.sso.base.constants.SSOConstants;
import com.sfsctech.core.auth.sso.base.properties.SSOProperties;
import com.sfsctech.core.auth.sso.base.token.extractor.JwtCookieTokenExtractor;
import com.sfsctech.core.auth.sso.base.token.extractor.JwtHeaderTokenExtractor;
import com.sfsctech.core.auth.sso.base.token.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.base.token.loader.JwtCookieTokenLoader;
import com.sfsctech.core.auth.sso.base.token.loader.JwtHeaderTokenLoader;
import com.sfsctech.core.auth.sso.base.token.loader.TokenLoader;
import com.sfsctech.core.auth.sso.server.handler.LoginSuccessHandler;
import com.sfsctech.core.auth.sso.server.handler.LogoutExecuteHandler;
import com.sfsctech.core.auth.sso.server.handler.LogoutSuccessHandler;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.support.jwt.handler.JwtFactory;
import com.sfsctech.support.jwt.properties.JwtProperties;
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
    public JwtFactory jwtTokenFactory() {
        return new JwtFactory(applicationInitialize.getAppName(), properties, factory);
    }

    @Bean
    public TokenLoader tokenLoader() {
        if (config.auth().getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
            return new JwtCookieTokenLoader();
        } else {
            return new JwtHeaderTokenLoader();
        }
    }

    @Bean
    public TokenExtractor tokenExtractor() {
        if (config.auth().getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
            return new JwtCookieTokenExtractor();
        } else {
            return new JwtHeaderTokenExtractor();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.authConfigure(http)) {
            // 无状态session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 禁用缓存
                    .and().headers().cacheControl();
            // 登出处理
            http.logout().addLogoutHandler(new LogoutExecuteHandler(tokenExtractor(), jwtTokenFactory(), applicationInitialize, factory));

            // jwt通过Cookie保持，登出后销毁Cookie
            if (config.auth().getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
                http.logout().deleteCookies(SSOConstants.TOKEN_IDENTIFY_COOKIE);
            }
        }
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(factory, jwtTokenFactory(), tokenLoader(), config.getWelcomeFile(), applicationInitialize);
    }

    @Override
    protected org.springframework.security.web.authentication.logout.LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler(config.getWelcomeFile());
    }
}
