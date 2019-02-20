package com.sfsctech.core.auth.sso.config;

import com.sfsctech.core.auth.base.config.AuthConfig;
import com.sfsctech.core.auth.base.config.BaseWebSecurityConfig;
import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.filter.CertificateFilter;
import com.sfsctech.core.auth.sso.handler.LoginSuccessHandler;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
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
@Import({AuthConfig.class, SSOProperties.class, JwtProperties.class, CacheConfig.class})
public class SSOConfig extends BaseWebSecurityConfig {

    @Autowired
    private SSOProperties ssoProperties;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Bean
    public CertificateFilter certificateFilter() {
        SkipPathRequestMatcher requestMatcher = new SkipPathRequestMatcher();
        CertificateFilter filter = new CertificateFilter();
        return new JwtAuthenticationTokenFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.basicConfigure(http)) {
            // 无状态session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 禁用缓存
                    .and().headers().cacheControl();
            // jwt通过Cookie保持，登出后销毁Cookie
            if (ssoProperties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
                http.logout().deleteCookies("JSESSIONID," + SSOConstants.COOKIE_TOKEN_NAME);
            }

            // 添加JWT filter
//            http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginSuccessHandler(factory, ssoProperties, config.getWelcomeFile());
    }
}
