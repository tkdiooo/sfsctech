package com.sfsctech.dubbo.base.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.dubbo.base.condition.MultipleProtocolCondition;
import com.sfsctech.dubbo.base.condition.SingleProtocolCondition;
import com.sfsctech.dubbo.base.constants.DubboConstants;
import com.sfsctech.dubbo.base.logger.filter.TraceNoFilter;
import com.sfsctech.dubbo.base.properties.DubboProperties;
import com.sfsctech.dubbo.base.serialize.KryoSerializationOptimizer;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.ClassUtil;
import com.sfsctech.support.common.util.FileUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class DubboxConfigurer
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@Configuration
@Import(DubboProperties.class)
public class DubboxConfig {

    private static final Logger logger = LoggerFactory.getLogger(DubboxConfig.class);

    @Autowired
    private DubboProperties properties;

    @Autowired
    private ApplicationInitialize application;

    @Autowired
    public void initialize() throws IOException {
        DubboProperties.DevSetting devSetting = properties.getDevSetting();
        String filePath = devSetting.getSystemPath() + "dubbo-resolve.properties";
        List<DubboProperties.DevSetting.Develop> list;
        // 添加文件
        if (application.getActive().equals("dev") && null != (list = devSetting.getDevelop())) {
            Set<String> lines;
            if (FileUtil.isExists(filePath)) {
                lines = new TreeSet<>(FileUtil.readFileToLines(filePath));
            } else {
                lines = new TreeSet<>();
            }
            list.forEach(inf -> {
                Set<Class<?>> cls = ClassUtil.getClasses(inf.getInfPackage());
                cls.forEach(s -> lines.add(s.getName() + LabelConstants.EQUAL + inf.getName() + LabelConstants.COLON + LabelConstants.DOUBLE_SLASH + "localhost" + LabelConstants.COLON + inf.getPort()));
            });
            FileUtil.writeLines(new File(filePath), lines, false);
        }
        // 删除文件
        else if (!application.getActive().equals("dev") && FileUtil.isExists(filePath)) {
            FileUtil.deleteQuietly(new File(filePath));
        }
    }

    /**
     * &lt;dubbo:application&gt;
     * dubbo 应用配置
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        config.setLogger(properties.getApplication().getLogger());
        config.setName(properties.getApplication().getName());
        logger.info("dubbo 应用配置：" + config.toString());
        return config;
    }

    /**
     * &lt;dubbo:registry&gt;
     * dubbo 注册中心配置
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        // 注册中心采用的协议
        config.setProtocol(properties.getRegistry().getProtocol());
        // 注册中心链接地址
        config.setAddress(properties.getRegistry().getAddress());
        // 注册中心启动时是否检查
        config.setCheck(properties.getRegistry().isCheck());
        // 是否向注册中心注册服务
        config.setRegister(properties.getRegistry().isRegister());
        // 是否向注册中心订阅服务
        config.setSubscribe(properties.getRegistry().isSubscribe());
        // 注册中心请求超时时间
        config.setTimeout(properties.getRegistry().getTimeout());
        logger.info("dubbo 注册中心配置：" + config.toString());
        return config;
    }

    /**
     * &lt;dubbo:protocol&gt;
     * 服务协议配置(多个)
     *
     * @return ProtocolConfig
     */
    @Bean(name = "MultipleProtocolConfig")
    @Conditional(MultipleProtocolCondition.class)
    public Object MultipleProtocolConfig() {
        properties.getProtocol().getMultiple().forEach((key, value) -> {
            // 注册至spring上下文
            SpringContextUtil.registerBeanDefinition(key, value.getClass().getName());
            ProtocolConfig config = (ProtocolConfig) SpringContextUtil.getBean(key);
            BeanUtil.copyPropertiesNotEmpty(config, value);
            // 服务器端实现类型
            if (StringUtil.isNotBlank(value.getServer()))
                config.setServer(value.getServer());
            // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
            if (DubboProperties.SerializeOptimizer.Kryo.equals(properties.getProtocol().getOptimizer())) {
                config.setSerialization(DubboConstants.SERIALIZE_KRYO);
                config.setOptimizer(KryoSerializationOptimizer.class.getName());
            }
            logger.info("dubbo 服务协议配置[" + key + "]：" + config.toString());
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
        // 服务器端实现类型
        if (StringUtil.isNotBlank(properties.getProtocol().getSingle().getServer()))
            config.setServer(properties.getProtocol().getSingle().getServer());
        // Kryo序列化实现，需要注册接口SerializationOptimizer，添加需要序列化的类
        if (DubboProperties.SerializeOptimizer.Kryo.equals(properties.getProtocol().getOptimizer())) {
            config.setSerialization(DubboConstants.SERIALIZE_KRYO);
            config.setOptimizer(KryoSerializationOptimizer.class.getName());
        }
        // 线程池类型
        if (null != properties.getProtocol().getThreadPool()) {
            config.setThreadpool(properties.getProtocol().getThreadPool().name());
        }
        // 服务最大线程池
        if (null != properties.getProtocol().getThreads()) {
            config.setThreads(properties.getProtocol().getThreads());
        }
        // 信息线程模型派发方式
        if (null != properties.getProtocol().getDispatcher()) {
            config.setDispatcher(properties.getProtocol().getDispatcher().name());
        }
        logger.info("dubbo 服务协议配置：" + config.toString());
        return config;
    }

    /**
     * dubbo 添加filter
     *
     * @return ProviderConfig
     */
    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig config = new ProviderConfig();
        config.setFilter("-exception,ExceptionHandler");
        Map<String, String> params = new HashMap<>();
        params.put(DubboConstants.HYSTRIX_CONCURRENCY, properties.getRpc().getConcurrency().toString());
        config.setParameters(params);
        logger.info("dubbo 添加filter：" + config.toString());
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
    (@Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + DubboConstants.DUBBO_RPC_SERVICE_PACKAGE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String
             servicePackage) {
        AnnotationBean config = new AnnotationBean();
        config.setPackage(servicePackage);
        logger.info("dubbo 注解配置服务：" + config.toString());
        return config;
    }


    /**
     * 日志跟踪 - 不过滤静态资源、页面模板、和druid
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new TraceNoFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("traceNoFilter");
        registration.setOrder(TraceNoFilter.FILTER_ORDER);
        return registration;
    }

}
