package com.sfsctech.core.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.spring.config.SpringConfig;
import com.sfsctech.core.web.filter.ActionFilter;
import com.sfsctech.core.web.initialize.PropertiesInitialize;
import com.sfsctech.core.web.initialize.WebResourceInitialize;
import com.sfsctech.core.web.properties.WebsiteProperties;
import com.sfsctech.core.web.tools.cookie.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class WebConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import({SpringConfig.class, WebsiteProperties.class, WebResourceInitialize.class, PropertiesInitialize.class})
public class WebConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private FastJsonHttpMessageConverter fastJsonConverter;

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
    public void addViewControllers(@Nullable @NotNull ViewControllerRegistry registry) {
        registry.addViewController(LabelConstants.FORWARD_SLASH).setViewName("redirect:" + websiteProperties.getSupport().getWelcomeFile());
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 设置Spring MVC URL 匹配规则
     *
     * @param configurer PathMatchConfigurer
     */
    @Override
    public void configurePathMatch(@Nullable @NotNull PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
    }

    /**
     * 配置FastJson
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(@Nullable @NotNull List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(fastJsonConverter);
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
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> errorPageRegistrar() {
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.NOT_FOUND));
            ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, (FilterHandler.ERROR_PATH + LabelConstants.FORWARD_SLASH + LabelConstants.INTERNAL_SERVER_ERROR));
            container.addErrorPages(error401Page, error403Page, error404Page, error405Page, error500Page);
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
        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(dispatcherServlet);
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
    @ConfigurationProperties(prefix = "server.servlet.session.cookie")
    public Config cookieConfig() {
        return new Config();
    }

    @Bean
    public FilterRegistrationBean ActionFilter() {
        FilterRegistrationBean<ActionFilter> registration = new FilterRegistrationBean<>(new ActionFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("ActionFilter");
        registration.setOrder(ActionFilter.FILTER_ORDER);
        return registration;
    }
}
