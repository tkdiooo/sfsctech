package com.sfsctech.resources;

import com.sfsctech.constants.LabelConstants;
import com.sfsctech.resources.filter.SecurityFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@SpringBootApplication
public class StaticResources {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SecurityFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SecurityFilter");
        registration.setOrder(1);
        return registration;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(StaticResources.class).web(true).run(args);
    }
}
