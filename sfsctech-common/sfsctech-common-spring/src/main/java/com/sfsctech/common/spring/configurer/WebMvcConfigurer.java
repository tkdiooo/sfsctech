package com.sfsctech.common.spring.configurer;

import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.util.ByteSizeUtil;
import com.sfsctech.common.spring.properties.Application;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

/**
 * Class WebMvcConfigurer
 *
 * @author 张麒 2017/3/16.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackages = "com.sfsctech.common")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private MessageSource messageSource;

    @Autowired
    private Application application;

    /**
     * 默认首页
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(LabelConstants.FORWARD_SLASH).setViewName("forward:/index");
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
     * Spring MVC Validator 加载：添加自定义验证工厂类
     *
     * @return Validator
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 添加自定义验证工厂类
     *
     * @return LocalValidatorFactoryBean
     */
    @Bean
    public LocalValidatorFactoryBean initLocalValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    /**
     * 设置Servlet配置
     *
     * @param servlet DispatcherServlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet servlet) {
        // 404请求抛出NoHandlerFoundException
        servlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet);
        // 上传文件配置
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(application.getLocation(), ByteSizeUtil.parseBytesSize(application.getMaxFileSize()), ByteSizeUtil.parseBytesSize(application.getMaxRequestSize()), application.getFileSizeThreshold());
        registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    /**
     * 添加国际化资源<br/>
     * <b>Note<b/> 静态方法保证其在系统启动时就调用
     *
     * @return MessageSource
     */
    @Bean
    public static MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(CommonConstants.RESOURCES_I18N_PATH);
        messageSource.setDefaultEncoding(LabelConstants.UTF8);
        messageSource.setCacheSeconds(600);
        return messageSource;
    }
}
