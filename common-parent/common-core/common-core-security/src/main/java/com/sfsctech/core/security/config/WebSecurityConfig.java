package com.sfsctech.core.security.config;

import com.sfsctech.core.security.csrf.CsrfSecurityRequestMatcher;
import com.sfsctech.core.security.test.MyFilterSecurityInterceptor;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Class WebSecurityConfig
 *
 * @author 张麒 2019-1-21.
 * @version Description:
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private WebsiteProperties websiteProperties;


    /**
     * 用户认证
     *
     * @return
     */
    @Bean("userDetailsService")
    @ConditionalOnProperty(name = "website.security.csrf.enabled", havingValue = "Custom")
    public UserDetailsService userDetailsService() {
        try {
            return properties.authentication().getUserDetailsService().newInstance();
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
        if (null != properties.authentication().getPasswordEncoder()) {
            try {
                return properties.authentication().getPasswordEncoder().newInstance();
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

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (properties.http().isDisable()) {
            http.httpBasic().disable();
        } else {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer = http.authorizeRequests();
            //任何人都可以访问
            if (ListUtil.isNotEmpty(properties.http().getInterceptExcludePath())) {
                configurer.antMatchers(properties.http().getInterceptExcludePath().toArray(new String[0])).permitAll();
            }
            // 登录成功跳转：自定义页面
            if (null != properties.authentication().getAuthenticationSuccessHandler()) {
                configurer.and().formLogin().successHandler(properties.authentication().getAuthenticationSuccessHandler().newInstance());
            }
            // 登录成功跳转：指定页面
            else if (StringUtil.isNotBlank(websiteProperties.getSupport().getWelcomeFile())) {
                configurer.and().formLogin().loginPage(websiteProperties.getSupport().getWelcomeFile());
            }
            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

        }
        http.csrf().disable();
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .requireCsrfProtectionMatcher(new CsrfSecurityRequestMatcher());
    }
}
