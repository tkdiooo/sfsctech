package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.auth.base.config.BaseWebSecurityConfig;
import com.sfsctech.core.auth.base.handler.AuthenticationFailureHandler;
import com.sfsctech.core.auth.session.handler.LogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Class LoginSecurityConfig
 *
 * @author 张麒 2019-2-22.
 * @version Description:
 */
public abstract class AuthSecurityConfig extends BaseWebSecurityConfig {

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

    protected boolean authConfigure(HttpSecurity http) throws Exception {
        boolean bool = super.basicConfigure(http);
        if (bool) {
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
                formLogin.failureHandler(new AuthenticationFailureHandler());
            }
            http.logout().logoutSuccessHandler(new LogoutSuccessHandler(config.getWelcomeFile()));
        }
        return bool;
    }


    protected abstract AuthenticationSuccessHandler authenticationSuccessHandler();
}
