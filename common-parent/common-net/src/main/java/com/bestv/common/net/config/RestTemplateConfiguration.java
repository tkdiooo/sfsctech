//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import com.bestv.common.net.domain.CommonNetConfig;
import com.bestv.common.net.execute.factory.http.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({RestTemplateFactoryConfiguration.class, CommonNetConfigConfiguration.class})
public class RestTemplateConfiguration {

    @Autowired
    private CommonNetConfig commonNetConfig;
    @Autowired
    private RestTemplateFactory restTemplateFactory;

    public RestTemplateConfiguration() {
    }

    @Bean(
            name = {"httpClient"}
    )
    @LoadBalanced
    public RestTemplate getHttpClient() {
        return this.restTemplateFactory.createRestTemplate(this.commonNetConfig);
    }
}
