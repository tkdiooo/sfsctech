package com.sfsctech.core.auth.base.config;

import com.sfsctech.core.auth.base.point.LoginUrlAuthenticationEntryPoint;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
@Import({AuthProperties.class, SkipPathConfig.class})
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected SkipPathConfig config;

    /**
     * 用户认证
     *
     * @return
     */
    @Bean("userDetailsService")
    public UserDetailsService userDetailsService() {
        if (null != config.auth().getLogin().getUserDetailsService()) {
            try {
                return config.auth().getLogin().getUserDetailsService().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 密码校验规则
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        if (null != config.auth().getLogin().getPasswordEncoder()) {
            try {
                return config.auth().getLogin().getPasswordEncoder().newInstance();
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
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = userDetailsService();
        if (null != userDetailsService) {
            // 自定义用户校验
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
    }

    protected boolean basicConfigure(HttpSecurity http) throws Exception {
        if (config.auth().isDisable()) {
            http.httpBasic().disable();
        } else {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer = http.authorizeRequests();
            // 任何人都可以访问
            configurer.antMatchers(config.getExcludePath().toArray(new String[0])).permitAll();
            // 自定义登录页面处理
            LoginUrlAuthenticationEntryPoint point = new LoginUrlAuthenticationEntryPoint(config.auth().getLogin().getUrl());
            point.setForceHttps(config.auth().getLogin().isHttps());
            point.setUseForward(config.auth().getLogin().isUseForward());
            configurer.and().exceptionHandling().authenticationEntryPoint(point);
            // 其余所有请求都被拦截
            configurer.anyRequest().authenticated();
            // csrf配置
            configurer.and().csrf().disable();
            // cors配置
            configurer.and().cors().disable();
        }
        return !config.auth().isDisable();
    }

}
