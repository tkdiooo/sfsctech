package com.sfsctech.common.core.security.config;

import com.sfsctech.common.core.base.constants.LabelConstants;
import com.sfsctech.common.core.base.filter.FilterHandler;
import com.sfsctech.common.core.exception.controller.GlobalErrorController;
import com.sfsctech.common.core.exception.controller.GlobalExceptionHandler;
import com.sfsctech.common.core.security.condition.DDOCCondition;
import com.sfsctech.common.core.security.factory.HandlerMethodFactory;
import com.sfsctech.common.core.security.filter.DDOCFilter;
import com.sfsctech.common.core.security.filter.XSSFilter;
import com.sfsctech.common.core.security.interceptor.SecurityInterceptor;
import com.sfsctech.common.core.security.properties.StartProperties;
import com.sfsctech.common.core.security.resolver.RequestAttributeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Class SecurityConfigurer
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
@Configuration
@Import({StartProperties.class, GlobalErrorController.class, GlobalExceptionHandler.class})
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private StartProperties properties;

    /**
     * 自定义参数解析器
     *
     * @param argumentResolvers HandlerMethodArgumentResolver
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestAttributeResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 添加拦截器
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (properties.getProperties().getCsrf().isOpen()) {
            SecurityInterceptor securityInterceptor = new SecurityInterceptor();
            securityInterceptor.setExcludesPattern(properties.getProperties().getCsrf().getVerifyExcludePath());
            securityInterceptor.setVerifyPattern(properties.getProperties().getCsrf().getRequiredVerifyPath());
            // 多个拦截器组成一个拦截器链
            // addPathPatterns 用于添加拦截规则
            // excludePathPatterns 用户排除拦截
            registry.addInterceptor(securityInterceptor).addPathPatterns(LabelConstants.SLASH_DOUBLE_STAR).excludePathPatterns(FilterHandler.getCSRFExcludes());
            super.addInterceptors(registry);
        }
    }

    /**
     * 自定义HandlerMethodFactory，用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
     */
    @Bean
    public HandlerMethodFactory HandlerMethodFactory() {
        return new HandlerMethodFactory();
    }

    /**
     * XSS过滤 - 不过滤静态资源、页面模板、和druid
     */
    @Bean
    public FilterRegistrationBean XSSFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XSSFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("XSSFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    /**
     * DDOC过滤 -
     */
    @Bean
    @Conditional(DDOCCondition.class)
    public FilterRegistrationBean DDOCFilter() {
        DDOCFilter filter = new DDOCFilter();
        filter.setProperties(properties.getProperties());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("DDOCFilter");
        return registration;
    }
}
