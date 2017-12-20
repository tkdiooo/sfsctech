package com.sfsctech.configurer;

import com.sfsctech.constants.LabelConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

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
    public RestTemplateBuilder restTemplateBuilder() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName(LabelConstants.UTF8));
        return new RestTemplateBuilder().setReadTimeout(5000).setConnectTimeout(5000).additionalMessageConverters(converter);
    }
}
