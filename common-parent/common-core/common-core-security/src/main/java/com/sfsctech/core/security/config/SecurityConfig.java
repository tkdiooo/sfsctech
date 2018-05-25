package com.sfsctech.core.security.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.exception.controller.GlobalErrorController;
import com.sfsctech.core.exception.controller.GlobalExceptionHandler;
import com.sfsctech.core.security.factory.HandlerMethodFactory;
import com.sfsctech.core.security.filter.DDOCFilter;
import com.sfsctech.core.security.filter.RequestFilter;
import com.sfsctech.core.security.filter.XSSFilter;
import com.sfsctech.core.security.interceptor.CsrfSecurityInterceptor;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.security.properties.StartProperties;
import com.sfsctech.core.security.resolver.RequestAttributeResolver;
import com.sfsctech.support.common.util.NumberUtil;
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
        SecurityProperties.Csrf csrf = properties.getProperties().getCsrf();
        if (null != csrf && csrf.isStart()) {
            CsrfSecurityInterceptor securityInterceptor = new CsrfSecurityInterceptor();
            // 验证排除
            securityInterceptor.setVerifyExcludePath(properties.getProperties().getCsrf().getVerifyExcludePath());
            // 强制验证路径
            securityInterceptor.setRequiredVerifyPath(properties.getProperties().getCsrf().getRequiredVerifyPath());
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
    @ConditionalOnProperty(name = "website.security.csrf.start", havingValue = "true")
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
        registration.setOrder(10);
        return registration;
    }

    /**
     * 请求安全过滤
     */
    @Bean
    public FilterRegistrationBean RequestFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("RequestFilter");
        registration.setOrder(NumberUtil.INTEGER_ONE);
        return registration;
    }

    /**
     * DDOC过滤
     */
    @Bean
    @ConditionalOnProperty(name = "website.security.ddos.start", havingValue = "true")
    public FilterRegistrationBean DDOCFilter() {
        DDOCFilter filter = new DDOCFilter(properties.getProperties().getDdos());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("DDOCFilter");
        registration.setOrder(2);
        return registration;
    }
}
