package com.sfsctech.common.logback.configurer;

import com.sfsctech.common.logback.trace.filter.TraceNoFilter;
import com.sfsctech.common.logback.rmt.listener.LogbackConfigListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class LogbackConfigurer
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
@Configuration
public class LogbackConfigurer {

    /**
     * 注册logback rmt监听
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<LogbackConfigListener> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new LogbackConfigListener());
        return registration;
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceNoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("traceNoFilter");
        return registration;
    }
}
