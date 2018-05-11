package com.sfsctech.common.core.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.common.core.base.constants.LabelConstants;
import com.sfsctech.common.core.base.filter.FilterHandler;
import com.sfsctech.common.core.spring.config.SpringConfig;
import com.sfsctech.common.core.web.cookie.Config;
import com.sfsctech.common.core.web.initialize.PropertiesInitialize;
import com.sfsctech.common.core.web.initialize.WebResourceInitialize;
import com.sfsctech.common.core.web.properties.WebsiteProperties;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Class WebConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import({SpringConfig.class, MultipartProperties.class, WebResourceInitialize.class, WebsiteProperties.class, PropertiesInitialize.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private SerializeConfig serializeConfig;

    @Autowired
    private Validator validator;

    @Autowired
    private MultipartProperties multipart;

    @Autowired
    private WebsiteProperties websiteProperties;

    /**
     * 默认首页
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(LabelConstants.FORWARD_SLASH).setViewName("forward:" + websiteProperties.getSupport().getWelcomeFile());
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
        converters.add(new ByteArrayHttpMessageConverter());

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue
        );
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
        super.configureMessageConverters(converters);
    }

    /**
     * Spring MVC Validator 加载：添加自定义验证工厂类
     *
     * @return Validator
     */
    @Override
    public Validator getValidator() {
        return this.validator;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.NOT_FOUND));
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                ((TomcatEmbeddedServletContainerFactory) container)
                        .addContextCustomizers((TomcatContextCustomizer) context -> context.setCookieProcessor(new LegacyCookieProcessor()));
            }
        });
    }

    /**
     * 设置Servlet配置
     *
     * @param dispatcherServlet DispatcherServlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        // 404请求抛出NoHandlerFoundException
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.addInitParameter("defaultHtmlEscape", LabelConstants.TRUE);
        // 上传文件配置
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件上传临时路径
        factory.setLocation(multipart.getLocation());
        // 单个文件大小
        factory.setMaxFileSize(multipart.getMaxFileSize());
        // 批量文件总大小
        factory.setMaxRequestSize(multipart.getMaxRequestSize());
        // 文件写入磁盘的阈值
        factory.setFileSizeThreshold(multipart.getFileSizeThreshold());
        registration.setMultipartConfig(factory.createMultipartConfig());
        return registration;
    }

    @Bean()
    @ConfigurationProperties(prefix = "server.session.cookie")
    public Config cookieConfig() {
        return new Config();
    }
}
