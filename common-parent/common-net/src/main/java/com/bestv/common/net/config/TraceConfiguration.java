//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({TraceInfoInjectAspectJ.class})
public class TraceConfiguration extends WebMvcConfigurerAdapter {
    public TraceConfiguration() {
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceHandlerInterceptor());
    }
}
