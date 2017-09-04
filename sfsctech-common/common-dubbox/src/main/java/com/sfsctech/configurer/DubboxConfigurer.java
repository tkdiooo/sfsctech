package com.sfsctech.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.dubbox.properties.DubboConfig;
import com.sfsctech.dubbox.properties.DubboProperties;
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
@ComponentScan(basePackageClasses = DubboProperties.class)
public class DubboxConfigurer {

    @Autowired
    private DubboProperties properties;

    /**
     * <code><</code>dubbo:application<code>></code>
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setLogger(properties.getApplication().getLogger());
        config.setName(properties.getApplication().getName());
        return config;
    }

    /**
     * <code><</code>dubbo:registry<code>></code>
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setAddress(properties.getRegistry().getAddress());
        config.setCheck(properties.getRegistry().isCheck());
        config.setRegister(properties.getRegistry().isRegister());
        config.setSubscribe(properties.getRegistry().isSubscribe());
        config.setTimeout(properties.getRegistry().getTimeout());
        return config;
    }

    /**
     * <code><</code>dubbo:protocol<code>></code>
     *
     * @return ProtocolConfig
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName(properties.getProtocol().getName());
        config.setPort(properties.getProtocol().getPort());
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        if (properties.getProtocol().isKryo()) {
            config.setSerialization("kryo");
            config.setOptimizer("com.sfsctech.dubbox.serialize.KryoSerializationOptimizer");
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
     * @return AnnotationBean
     */
    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(DubboConfig.getServicePackage());
        return annotationBean;
    }
}
