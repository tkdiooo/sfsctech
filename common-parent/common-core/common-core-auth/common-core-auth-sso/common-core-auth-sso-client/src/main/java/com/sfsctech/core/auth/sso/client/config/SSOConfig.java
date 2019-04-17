package com.sfsctech.core.auth.sso.client.config;

import com.sfsctech.core.auth.base.config.BaseWebSecurityConfig;
import com.sfsctech.core.auth.base.config.SkipPathConfig;
import com.sfsctech.core.auth.base.handler.AuthenticationFailureHandler;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.core.auth.sso.base.inf.SSOInterface;
import com.sfsctech.core.auth.sso.base.properties.JwtProperties;
import com.sfsctech.core.auth.sso.base.properties.SSOProperties;
import com.sfsctech.core.auth.sso.base.token.extractor.JwtCookieTokenExtractor;
import com.sfsctech.core.auth.sso.base.token.extractor.JwtHeaderTokenExtractor;
import com.sfsctech.core.auth.sso.base.token.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.client.filter.JwtTokenAuthenticationProcessingFilter;
import com.sfsctech.core.auth.sso.client.handler.LogoutPrepareHandler;
import com.sfsctech.core.auth.sso.client.matcher.SkipPathRequestMatcher;
import com.sfsctech.core.auth.sso.client.provider.JwtAuthenticationProvider;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Class SSOConfig
 *
 * @author 张麒 2019-2-22.
 * @version Description:
 */
@Import({SkipPathConfig.class, SSOProperties.class, JwtProperties.class, CacheConfig.class})
public class SSOConfig extends BaseWebSecurityConfig {

    @Autowired
    private JwtProperties settings;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Autowired(required = false)
    private SSOInterface ssoInterface;

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
                factory,
                // 认证失败处理（跳转登录页）
                new AuthenticationFailureHandler(config.auth().getLogin().getUrl()),
                // token提取器
                tokenExtractor(),
                // Jwt配置
                settings,
                // 拦截映射
                new SkipPathRequestMatcher(config.getExcludePath(), HttpUtil.getRootPattern()));
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    public TokenExtractor tokenExtractor() {
        if (config.auth().getSessionKeep().equals(AuthProperties.SessionKeep.Cookie)) {
            return new JwtCookieTokenExtractor();
        } else {
            return new JwtHeaderTokenExtractor();
        }
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new JwtAuthenticationProvider(ssoInterface));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (super.basicConfigure(http)) {
            // 无状态session策略
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 禁用缓存
                    .and().headers().cacheControl();
            // 添加JWT filter
            http.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
            // logout路径重定向
            http.logout()
                    .addLogoutHandler(new LogoutPrepareHandler(config.auth().getLogout().getUrl()));
        }
    }
}
