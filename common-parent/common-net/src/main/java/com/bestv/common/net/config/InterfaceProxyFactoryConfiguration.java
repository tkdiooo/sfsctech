//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import com.bestv.common.net.execute.factory.InterfaceProxyFactory;
import com.bestv.common.net.execute.factory.http.SpringHttpInterfaceProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * 接口代理工厂配置
 */
@Configuration
@Import({RestTemplateConfiguration.class})
public class InterfaceProxyFactoryConfiguration {
    @Autowired
    private RestTemplate httpClient;

    public InterfaceProxyFactoryConfiguration() {
    }

    @Bean
    public InterfaceProxyFactory getInterfaceProxyFactory() {
        return new SpringHttpInterfaceProxyFactory(this.httpClient);
    }
}
