package com.sfsctech.core.security.config;

import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

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

//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        if (properties.http().isDisable()) {
//            http.httpBasic().disable();
//        } else {
//            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer = http.authorizeRequests();
//            //任何人都可以访问
//            if (ListUtil.isNotEmpty(properties.http().getInterceptExcludePath())) {
//                configurer.antMatchers(properties.http().getInterceptExcludePath().toArray(new String[0])).permitAll();
//            }
//            // 登录成功跳转：自定义页面
//            if (null != properties.authentication().getAuthenticationSuccessHandler()) {
//                configurer.and().formLogin().successHandler(properties.authentication().getAuthenticationSuccessHandler().newInstance());
//            }
//            // 登录成功跳转：指定页面
//            else if (StringUtil.isNotBlank(websiteProperties.getSupport().getWelcomeFile())) {
//                configurer.and().formLogin().loginPage(websiteProperties.getSupport().getWelcomeFile());
//            }
//            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
//        }
        http.httpBasic().disable();
        http.csrf().disable();
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .requireCsrfProtectionMatcher(new CsrfSecurityRequestMatcher());
    }
}
