package com.sfsctech.configurer;

import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.security.factory.HandlerMethodFactory;
import com.sfsctech.security.tools.SOAExistsCondition;
import com.sfsctech.security.filter.SSOFilter;
import com.sfsctech.security.filter.XSSFilter;
import com.sfsctech.security.interceptor.SecurityInterceptor;
import com.sfsctech.security.resolver.RequestAttributeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
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
public class SecurityConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private List<HttpMessageConverter<?>> converters;

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
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns(LabelConstants.SLASH_DOUBLE_STAR).excludePathPatterns(SecurityConstants.getCSRFExcludes());
        super.addInterceptors(registry);
    }

    @Bean
    public HandlerMethodFactory HandlerMethodFactory() {
        return new HandlerMethodFactory();
    }

    @Bean
    public FilterRegistrationBean XSSFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XSSFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("XSSFilter");
        return registration;
    }

    @Bean
    @Conditional(SOAExistsCondition.class)
    public FilterRegistrationBean SSOFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SSOFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addInitParameter(SecurityConstants.FILTER_EXCLUDES_KEY, SecurityConstants.SERVER_SUFFIX + "/druid/*");
        return registration;
    }
}
