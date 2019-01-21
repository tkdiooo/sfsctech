package com.sfsctech.core.security.config;

import com.sfsctech.core.security.csrf.CsrfSecurityRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Class WebSecurityConfig
 *
 * @author 张麒 2019-1-21.
 * @version Description:
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .requireCsrfProtectionMatcher(new CsrfSecurityRequestMatcher());
    }
}
