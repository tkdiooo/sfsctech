package com.sfsctech.common.spring.configurer;

import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.spring.util.SpringUtil;
import com.sfsctech.common.util.ByteSizeUtil;
import com.sfsctech.common.spring.properties.Application;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
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
@ComponentScan(basePackageClasses = Application.class)
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private MessageSource messageSource;

    @Autowired
    private SpringUtil springUtil;

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
        LocalValidatorFactoryBean mvcValidator = new LocalValidatorFactoryBean();
        mvcValidator.setProviderClass(HibernateValidator.class);
        mvcValidator.setValidationMessageSource(messageSource);
        return mvcValidator;
    }

    /**
     * 设置Servlet配置
     *
     * @param servlet DispatcherServlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet servlet) {
        return springUtil.getServletRegistrationBean(servlet);
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
