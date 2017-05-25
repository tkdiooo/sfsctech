package com.sfsctech.common.dubbox.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.common.dubbox.properties.DubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackageClasses = DubboConfig.class)
public class DubboxConfigurer {

    @Autowired
    private DubboConfig dubboConfig;

    /**
     * <code><</code>dubbo:application<code>></code>
     *
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setLogger(dubboConfig.APPLICATION_LOGGER);
        config.setName(dubboConfig.APPLICATION_NAME);
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
        config.setAddress(dubboConfig.REGISTRY_ADDRESS);
        config.setCheck(dubboConfig.REGISTRY_CHECK);
        config.setRegister(dubboConfig.REGISTRY_REGISTRY);
        config.setSubscribe(dubboConfig.REGISTRY_SUBSCRIBE);
        config.setTimeout(dubboConfig.REGISTRY_TIMEOUT);
        return config;
    }

    /**
     * <code><</code>dubbo:protocol<code>></code>
     *
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName(dubboConfig.PROTOCOL_NAME);
        config.setPort(dubboConfig.PROTOCOL_PORT);
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        if (dubboConfig.IS_EMPLOY_KRYO) {
            config.setSerialization("kryo");
            config.setOptimizer("com.sfsctech.common.dubbox.serialize.KryoSerializationOptimizer");
        }
        return config;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig config = new ProviderConfig();
        config.setFilter(DubboConfig.getFILTERS());
        return config;
    }

    /**
     * <code><</code>dubbo:annotation<code>></code>
     * 所以含有@com.alibaba.dubbo.config.annotation.Service的注解类都应在此包中,多个包名可以使用英文逗号分隔.
     *
     * @return
     */
    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(DubboConfig.getAnnotationPackage());
        return annotationBean;
    }
}
