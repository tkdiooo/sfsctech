//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import com.bestv.common.net.execute.factory.http.CommonRestTemplateFactory;
import com.bestv.common.net.execute.factory.http.RestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateFactoryConfiguration {
    public RestTemplateFactoryConfiguration() {
    }

    @Bean
    public RestTemplateFactory getRestTemplateFactory() {
        return new CommonRestTemplateFactory();
    }
}
