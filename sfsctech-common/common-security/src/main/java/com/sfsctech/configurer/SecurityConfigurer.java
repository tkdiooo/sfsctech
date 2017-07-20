package com.sfsctech.configurer;

import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.security.filter.XSSFilter;
import com.sfsctech.security.interceptor.SecurityInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**").excludePathPatterns(LabelConstants.FORWARD_SLASH);
        super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new XSSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("XSSFilter");
        registration.addInitParameter(CommonConstants.FILTER_EXCLUDES, "/druid/*");
        return registration;
    }
}
