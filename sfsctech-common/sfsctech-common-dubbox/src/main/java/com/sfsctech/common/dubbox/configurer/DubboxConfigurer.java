package com.sfsctech.common.dubbox.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sfsctech.common.spring.properties.Application;
import com.sfsctech.common.util.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class DubboxConfigurer {

    @Resource
    private Application application;

    /**
     * <code><</code>dubbo:application<code>></code>
     *
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
//        config.setLogger(application.getDubboApplicationLogger());
//        config.setName(application.getDubboApplicationName());
        return config;
    }

    /*与<dubbo:registry/>相当*/
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
//        config.setAddress(env.getProperty("dubbo.registryAddress"));
//        config.setCheck();
//        config.setRegister();
//        config.setSubscribe();
//        config.setTimeout();
        return config;
    }
}
