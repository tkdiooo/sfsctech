package com.sfsctech.core.auth.base.config;

import com.sfsctech.core.auth.base.handler.LoginSuccessHandler;
import com.sfsctech.core.auth.base.point.LoginUrlAuthenticationEntryPoint;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import com.sfsctech.support.common.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class BaseWebSecurityConfig
 *
 * @author 张麒 2019-1-25.
 * @version Description:
 */
@Import(AuthProperties.class)
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthProperties properties;

    /**
     * 用户认证
     *
     * @return
     */
    @Bean("userDetailsService")
    @ConditionalOnProperty(name = "website.auth.pattern", havingValue = "Custom")
    public UserDetailsService userDetailsService() {
        try {
            return properties.getUserDetailsService().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 密码校验规则
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        if (null != properties.getPasswordEncoder()) {
            try {
                return properties.getPasswordEncoder().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    /**
     * 配置user-detail服务
     *
     * @param auth
     * @throws Exception
     */
    @Override
    @ConditionalOnBean(name = "userDetailsService")
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义用户校验
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (properties.isDisable()) {
            http.httpBasic().disable();
        } else {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer = http.authorizeRequests();
            // 任何人都可以访问
            if (ListUtil.isNotEmpty(properties.getExcludePath())) {
                configurer.antMatchers(properties.getExcludePath().toArray(new String[0])).permitAll();
            }
            // 自定义登录处理
            LoginUrlAuthenticationEntryPoint point = new LoginUrlAuthenticationEntryPoint(properties.getLogin().getUrl());
            point.setForceHttps(properties.getLogin().isHttps());
            point.setUseForward(properties.getLogin().isUseForward());
            configurer.and().exceptionHandling().authenticationEntryPoint(point);
            // 自定义登录成功处理
            if (null != properties.getLogin().getAuthenticationSuccessHandler()) {
                configurer.and().formLogin().successHandler(properties.getLogin().getAuthenticationSuccessHandler().newInstance());
            }
            // 默认登录成功处理
            else {
                configurer.and().formLogin().successHandler(new LoginSuccessHandler());
            }
            // 自定义登出处理
        }
    }
}
