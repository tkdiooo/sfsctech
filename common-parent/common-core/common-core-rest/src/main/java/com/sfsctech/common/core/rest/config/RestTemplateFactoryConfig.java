package com.sfsctech.common.core.rest.config;

import com.sfsctech.common.core.rest.factory.CommonRestTemplateFactory;
import com.sfsctech.common.core.rest.factory.RestTemplateFactory;
import com.sfsctech.common.core.rest.properties.RestProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * Class RestConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import(RestProperties.class)
public class RestTemplateFactoryConfig {

    @Bean
    public RestTemplateFactory restTemplateFactory(RestProperties properties) {
        return new CommonRestTemplateFactory(properties);
    }

    @Bean
    // TODO
    @ConditionalOnProperty(name = "synchronize", havingValue = "true")
    public RestTemplate restTemplate(RestTemplateFactory factory) {
        return factory.buildSimpleRest();
    }
}
