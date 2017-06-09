package com.sfsctech.configurer;

import com.sfsctech.logback.core.listener.LogbackConfigListener;
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
public class LogbackCoreConfigurer {

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
}
