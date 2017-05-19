package com.sfsctech.common.dubbox.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.common.spring.properties.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class DubboxConfigurer {

    @Autowired
    private Application application;

    /**
     * <code><</code>dubbo:application<code>></code>
     *
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setLogger(application.DUBBO_APPLICATION_LOGGER);
        config.setName(application.DUBBO_APPLICATION_NAME);
        return config;
    }

    /**
     * <code><</code>dubbo:registry<code>></code>
     *
     * @return
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setAddress(application.DUBBO_REGISTRY_ADDRESS);
        config.setCheck(application.DUBBO_REGISTRY_CHECK);
        config.setRegister(application.DUBBO_REGISTRY_REGISTRY);
        config.setSubscribe(application.DUBBO_REGISTRY_SUBSCRIBE);
        config.setTimeout(application.DUBBO_REGISTRY_TIMEOUT);
        return config;
    }

    /**
     * <code><</code>dubbo:protocol<code>></code>
     *
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig;
        if (null != application.DUBBO_PROTOCOL_PORT)
            protocolConfig = new ProtocolConfig(application.DUBBO_PROTOCOL_NAME, application.DUBBO_PROTOCOL_PORT);
        else
            protocolConfig = new ProtocolConfig(application.DUBBO_PROTOCOL_NAME);
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        protocolConfig.setSerialization(application.DUBBO_PROTOCOL_SERIALIZATION);
        protocolConfig.setOptimizer(application.DUBBO_PROTOCOL_OPTIMIZER);
        return protocolConfig;
    }

    /**
     * <code><</code>dubbo:annotation<code>></code>
     *
     * @return
     */
    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.sfsctech.mybatis.rpc.provider");//所以含有@com.alibaba.dubbo.config.annotation.Service的注解类都应在此包中,多个包名可以使用英文逗号分隔.
        return annotationBean;
    }
}
