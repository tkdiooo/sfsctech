package com.sfsctech.common.core.rest.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.common.core.rest.factory.CommonRestTemplateFactory;
import com.sfsctech.common.core.rest.factory.RestTemplateFactory;
import com.sfsctech.common.core.rest.properties.RestProperties;
import com.sfsctech.common.core.spring.config.SpringConfig;
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
@Import({RestProperties.class, SpringConfig.class})
public class RestTemplateFactoryConfig {

    @Bean
    public RestTemplateFactory restTemplateFactory(RestProperties properties, FastJsonHttpMessageConverter converter) {
        return new CommonRestTemplateFactory(properties, converter);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.rest.simple", havingValue = "true")
    public RestTemplate restTemplate(RestTemplateFactory factory) {
        return factory.buildSimpleRest();
    }
}
