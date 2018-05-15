package com.sfsctech.common.cloud.net.config;

import com.sfsctech.common.cloud.net.execute.factory.InterfaceProxyFactory;
import com.sfsctech.common.cloud.net.execute.factory.http.SpringHttpInterfaceProxyFactory;
import com.sfsctech.common.core.rest.config.RestTemplateFactoryConfig;
import com.sfsctech.common.core.rest.factory.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    /**
     * 创建接口代理工厂
     */
    @Bean
    public InterfaceProxyFactory getInterfaceProxyFactory() {
        return new SpringHttpInterfaceProxyFactory(restTemplateFactory.buildPoolRest());
    }
}
