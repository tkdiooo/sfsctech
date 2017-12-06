package com.sfsctech.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import com.sfsctech.dubbox.condition.MultipleProtocolCondition;
import com.sfsctech.dubbox.condition.SingleProtocolCondition;
import com.sfsctech.dubbox.config.DubboConfig;
import com.sfsctech.dubbox.properties.DubboProperties;
import com.sfsctech.dubbox.serialize.KryoSerializationOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@Configuration
@ComponentScan("com.sfsctech.dubbox")
public class DubboxConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(DubboxConfigurer.class);

    @Autowired
    private DubboProperties properties;

    /**
     * &lt;dubbo:application&gt;
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
     * &lt;dubbo:registry&gt;
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setProtocol(properties.getRegistry().getProtocol());
        config.setAddress(properties.getRegistry().getAddress());
        config.setCheck(properties.getRegistry().isCheck());
        config.setRegister(properties.getRegistry().isRegister());
        config.setSubscribe(properties.getRegistry().isSubscribe());
        config.setTimeout(properties.getRegistry().getTimeout());
        return config;
    }

    /**
     * &lt;dubbo:protocol&gt;
     *
     * @return ProtocolConfig
     */
    @Bean(name = "MultipleProtocolConfig")
    @Conditional(MultipleProtocolCondition.class)
    public Object MultipleProtocolConfig() {
        properties.getProtocol().getMultiple().forEach((key, value) -> {
            SpringContextUtil.registerBeanDefinition(key, value.getClass().getName());
            ProtocolConfig config = (ProtocolConfig) SpringContextUtil.getBean(key);
            BeanUtil.copyPropertiesNotEmpty(config, value);
            if (StringUtil.isNotBlank(value.getServer()))
                config.setServer(value.getServer());
            // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
            if (DubboProperties.SerializeOptimizer.Kryo.equals(properties.getProtocol().getOptimizer())) {
                config.setSerialization(DubboConstants.SERIALIZE_KRYO);
                config.setOptimizer(KryoSerializationOptimizer.class.getName());
            }
        });
        return new Object();
    }

    /**
     * &lt;dubbo:protocol&gt;
     *
     * @return ProtocolConfig
     */
    @Bean
    @Conditional(SingleProtocolCondition.class)
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName(properties.getProtocol().getSingle().getName());
        config.setPort(properties.getProtocol().getSingle().getPort());
        if (StringUtil.isNotBlank(properties.getProtocol().getSingle().getServer()))
            config.setServer(properties.getProtocol().getSingle().getServer());
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        if (DubboProperties.SerializeOptimizer.Kryo.equals(properties.getProtocol().getOptimizer())) {
            config.setSerialization(DubboConstants.SERIALIZE_KRYO);
            config.setOptimizer(KryoSerializationOptimizer.class.getName());
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
     * &lt;dubbo:annotation&gt;
     * 所以含有@com.alibaba.dubbo.config.annotation.Service的注解类都应在此包中,多个包名可以使用英文逗号分隔.
     *
     * @return AnnotationBean
     */
    @Bean
    public static AnnotationBean annotationBean
    (@Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_RPC_SERVICE_PACKAGE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String
             servicePackage) {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(servicePackage);
        return annotationBean;
    }
}
