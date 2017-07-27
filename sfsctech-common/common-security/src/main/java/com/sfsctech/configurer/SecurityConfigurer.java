package com.sfsctech.configurer;

import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.security.annotation.SOAExistsCondition;
import com.sfsctech.security.filter.SSOFilter;
import com.sfsctech.security.filter.XSSFilter;
import com.sfsctech.security.interceptor.SecurityInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Class SecurityConfigurer
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
@Configuration
public class SecurityConfigurer extends WebMvcConfigurerAdapter {

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
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns(LabelConstants.SLASH_DOUBLE_STAR).excludePathPatterns(SecurityConstants.SERVER_STATIC_PATH, SecurityConstants.ERROR_PATH + LabelConstants.SLASH_DOUBLE_STAR);
        super.addInterceptors(registry);
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
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SSOFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        return registration;
    }
}
