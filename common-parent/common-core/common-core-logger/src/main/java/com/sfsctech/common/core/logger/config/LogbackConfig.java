package com.sfsctech.common.core.logger.config;

import com.sfsctech.common.core.base.constants.LabelConstants;
import com.sfsctech.common.core.logger.logback.listener.LogbackConfigListener;
import com.sfsctech.common.core.logger.filter.TraceNoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Class LogbackRmtConfigurer
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
@Configuration
public class LogbackConfig {

    /**
     * Http注册logback
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<LogbackConfigListener> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new LogbackConfigListener());
        return registration;
    }

    /**
     * 日志跟踪 - 不过滤静态资源、页面模板、和druid
     *
     * @return
     */
    // TODO 该方案只适合dubbox项目，cloud项目有自己的解决方案，需要把相关代码移植到dubbox模块下
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new TraceNoFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("traceNoFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
