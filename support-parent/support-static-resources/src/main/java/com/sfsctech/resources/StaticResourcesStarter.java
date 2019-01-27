package com.sfsctech.resources;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.resources.filter.SecurityFilter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@SpringBootApplication
public class StaticResourcesStarter {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>(new SecurityFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SecurityFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(StaticResourcesStarter.class).web(WebApplicationType.SERVLET).run(args);
    }
}
