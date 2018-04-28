package com.sfsctech.configurer;

import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class RestConfigurer
 *
 * @author 张麒 2017-12-20.
 * @version Description:
 */
@Configuration
public class RestConfigurer {

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
//    @LoadBalanced
    public AsyncRestTemplate asyncRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(factory);
        List<HttpMessageConverter<?>> converterList = asyncRestTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
        return asyncRestTemplate;
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return new RestTemplateBuilder().setReadTimeout(5000).setConnectTimeout(5000).additionalMessageConverters(converter);
    }


}
