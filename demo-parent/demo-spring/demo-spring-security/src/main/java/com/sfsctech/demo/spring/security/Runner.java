package com.sfsctech.demo.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.security.web.csrf.CsrfTokenRepository;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableWebSecurity
@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }


    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable();
//            // @formatter:off
//            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//            successHandler.setTargetUrlParameter("redirectTo");
//
//            http.authorizeRequests()
//                    .antMatchers("/assets/**").permitAll()
//                    .antMatchers("/index").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin().loginPage("/index").successHandler(successHandler).and()
//                    .logout().logoutUrl("/logout").and()
//                    .httpBasic().and()
//                    .csrf().disable();
//            // @formatter:on
        }
    }
}
