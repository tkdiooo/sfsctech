package com.sfsctech.configurer;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.spring.properties.AppConfig;
import com.sfsctech.spring.util.JavaConfigUtil;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Class WebMvcConfigurer
 *
 * @author 张麒 2017/3/16.
 * @version Description:
 */
@Configuration
@EnableAsync
@ComponentScan("com.sfsctech.spring")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaConfigUtil javaConfigUtil;

    @Autowired
    private AppConfig appConfig;

    /**
     * 默认首页
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(LabelConstants.FORWARD_SLASH).setViewName("forward:" + appConfig.getWebsiteProperties().getSupport().getWelcomeFile());
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    /**
     * 设置Spring MVC URL 匹配规则
     *
     * @param configurer PathMatchConfigurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
    }

    /**
     * 配置FastJson
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue
        );
        fastJsonConfig.setSerializeConfig(serializeConfig());
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

    /**
     * Spring MVC Validator 加载：添加自定义验证工厂类
     *
     * @return Validator
     */
    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, (ExcludesConstants.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, (ExcludesConstants.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, (ExcludesConstants.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.NOT_FOUND));
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, (ExcludesConstants.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                ((TomcatEmbeddedServletContainerFactory) container)
                        .addContextCustomizers((TomcatContextCustomizer) context -> context.setCookieProcessor(new LegacyCookieProcessor()));
            }
        });
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 设置Servlet配置
     *
     * @param servlet DispatcherServlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet servlet) {
        return javaConfigUtil.getServletRegistrationBean(servlet);
    }

    /**
     * FastJson Enum处理
     *
     * @return
     */
    @Bean
    public SerializeConfig serializeConfig() {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.configEnumAsJavaBean(RpcConstants.Status.class);
        return serializeConfig;
    }

    /**
     * 添加国际化资源<br>
     * 静态方法保证其在系统启动时就调用
     *
     * @return MessageSource
     */
    @Bean
    public static MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(CommonConstants.RESOURCES_I18N_PATH);
        messageSource.setDefaultEncoding(LabelConstants.UTF8);
        // 缓存时间(秒)
        messageSource.setCacheSeconds(600);
        return messageSource;
    }
}
