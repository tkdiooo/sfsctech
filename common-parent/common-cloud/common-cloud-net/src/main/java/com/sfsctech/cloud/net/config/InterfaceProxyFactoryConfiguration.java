package com.sfsctech.cloud.net.config;

import com.sfsctech.cloud.net.execute.factory.InterfaceProxyFactory;
import com.sfsctech.cloud.net.execute.factory.http.SpringHttpInterfaceProxyFactory;
import com.sfsctech.core.rest.config.RestTemplateFactoryConfig;
import com.sfsctech.core.rest.factory.RestTemplateFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Class InterfaceProxyFactoryConfiguration
 * 接口代理工厂配置
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */
@Configuration
@Import({RestTemplateFactoryConfig.class})
public class InterfaceProxyFactoryConfiguration {

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(RestTemplateFactory restTemplateFactory) {
        return restTemplateFactory.buildPoolRest();
    }

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean(AsyncRestTemplate.class)
    public AsyncRestTemplate asyncRestTemplate(RestTemplateFactory restTemplateFactory) {
        // TODO Spring 5.0 异步
//        Mono.fromCallable(() -> new RestTemplate().getForObject(url, String.class)).subscribeOn(Schedulers.elastic()).subscribe(res -> {
//            System.out.println("monoRestTemplate响应为: " + res);
//        });

        return restTemplateFactory.buildAsyncRest();
    }

    /**
     * 创建接口代理工厂
     */
    @Bean
    public InterfaceProxyFactory getInterfaceProxyFactory(RestTemplate restTemplate) {
        return new SpringHttpInterfaceProxyFactory(restTemplate);
    }
}
