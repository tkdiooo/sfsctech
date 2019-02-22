package com.sfsctech.core.auth.base.config;

import com.sfsctech.core.auth.base.point.LoginUrlAuthenticationEntryPoint;
import com.sfsctech.core.auth.base.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Class BaseWebSecurityConfig
 *
 * @author 张麒 2019-1-25.
 * @version Description:
 */
@Import(AuthProperties.class)
public abstract class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected SkipPathConfig config;

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
            // 自定义登出页面
            configurer.and().logout().logoutUrl(config.auth().getLogout().getUrl());
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
