package com.sfsctech.common.dubbox.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.common.dubbox.properties.DubboConfig;
import com.sfsctech.common.util.SpringContextUtil;
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
@ComponentScan(basePackages = "com.sfsctech.common")
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
        config.setLogger(dubboConfig.DUBBO_APPLICATION_LOGGER);
        config.setName(dubboConfig.DUBBO_APPLICATION_NAME);
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
        config.setAddress(dubboConfig.DUBBO_REGISTRY_ADDRESS);
        config.setCheck(dubboConfig.DUBBO_REGISTRY_CHECK);
        config.setRegister(dubboConfig.DUBBO_REGISTRY_REGISTRY);
        config.setSubscribe(dubboConfig.DUBBO_REGISTRY_SUBSCRIBE);
        config.setTimeout(dubboConfig.DUBBO_REGISTRY_TIMEOUT);
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
        config.setName(dubboConfig.DUBBO_PROTOCOL_NAME);
        config.setPort(dubboConfig.DUBBO_PROTOCOL_PORT);
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        config.setSerialization(dubboConfig.DUBBO_PROTOCOL_SERIALIZATION);
        config.setOptimizer(dubboConfig.DUBBO_PROTOCOL_OPTIMIZER);
        return config;
    }

    /**
     * <code><</code>dubbo:annotation<code>></code>
     *
     * @return
     */
    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.sfsctech.mybatis.rpc.provider");//所以含有@com.alibaba.dubbo.config.annotation.Service的注解类都应在此包中,多个包名可以使用英文逗号分隔.
        return annotationBean;
    }
}
