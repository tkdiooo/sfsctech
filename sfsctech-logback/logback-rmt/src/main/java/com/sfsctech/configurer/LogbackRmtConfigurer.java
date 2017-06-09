package com.sfsctech.configurer;

import com.sfsctech.logback.rmt.filter.TraceNoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class LogbackRmtConfigurer
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
@Configuration
public class LogbackRmtConfigurer {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceNoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("traceNoFilter");
        return registration;
    }
}
