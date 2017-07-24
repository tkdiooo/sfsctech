package com.sfsctech.configurer;

import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.logback.core.listener.LogbackConfigListener;
import com.sfsctech.logback.rmt.filter.TraceNoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class LogbackRmtConfigurer
 *
 * @author 张麒 2017/6/9.
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
        FilterRegistrationBean registration = new FilterRegistrationBean(new TraceNoFilter());
        registration.addUrlPatterns(LabelConstants.FORWARD_SLASH + LabelConstants.STAR);
        registration.setName("traceNoFilter");
        registration.addInitParameter(SecurityConstants.FILTER_EXCLUDES_KEY, "*.jsp,/druid/*");
        return registration;
    }
}
