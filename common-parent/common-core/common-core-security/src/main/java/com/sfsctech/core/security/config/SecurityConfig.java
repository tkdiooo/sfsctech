package com.sfsctech.core.security.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.controller.GlobalErrorController;
import com.sfsctech.core.exception.controller.GlobalExceptionHandler;
import com.sfsctech.core.security.factory.HandlerMethodFactory;
import com.sfsctech.core.security.filter.DDOCFilter;
import com.sfsctech.core.security.filter.AccessFilter;
import com.sfsctech.core.security.filter.XSSFilter;
import com.sfsctech.core.security.interceptor.CsrfSecurityInterceptor;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.security.properties.StartProperties;
import com.sfsctech.core.security.resolver.RequestAttributeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
@Import({StartProperties.class, GlobalErrorController.class, GlobalExceptionHandler.class, CacheConfig.class})
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private StartProperties properties;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

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
        SecurityProperties.CSRF csrf = properties.getProperties().getCSRF();
        if (null != csrf && csrf.isEnabled()) {
            CsrfSecurityInterceptor securityInterceptor = new CsrfSecurityInterceptor();
            // 验证排除
            securityInterceptor.setVerifyExcludePath(csrf.getVerifyExcludePath());
            // 强制验证路径
            securityInterceptor.setRequiredVerifyPath(csrf.getRequiredVerifyPath());
            // 多个拦截器组成一个拦截器链
            // addPathPatterns 用于添加拦截规则
            // excludePathPatterns 用户拦截排除
            registry.addInterceptor(securityInterceptor).addPathPatterns(LabelConstants.SLASH_DOUBLE_STAR).excludePathPatterns(FilterHandler.getCSRFExcludes());
            super.addInterceptors(registry);
        }
    }

    /**
     * 自定义HandlerMethodFactory，用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
     */
    @Bean
    @ConditionalOnProperty(name = "website.security.csrf.enabled", havingValue = "true")
    public HandlerMethodFactory handlerMethodFactory() {
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
        registration.setOrder(XSSFilter.FILTER_ORDER);
        return registration;
    }

    /**
     * 访问安全过滤
     */
    @Bean
    public FilterRegistrationBean RequestFilter() {
        AccessFilter filter = new AccessFilter(properties.getProperties().getCrossDomain());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("RequestFilter");
        registration.setOrder(AccessFilter.FILTER_ORDER);
        return registration;
    }

    /**
     * DDOC过滤
     */
    @Bean
    @ConditionalOnProperty(name = "website.security.ddos.enabled", havingValue = "true")
    public FilterRegistrationBean DDOCFilter() {
        DDOCFilter filter = new DDOCFilter(properties.getProperties().getDDOS());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("DDOCFilter");
        registration.setOrder(DDOCFilter.FILTER_ORDER);
        return registration;
    }
}
