package com.sfsctech.website.jsp.interceptor;

import com.sfsctech.constants.LabelConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Class MvcConfigurer
 *
 * @author 张麒 2017/6/21.
 * @version Description:
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
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
        registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**").excludePathPatterns(LabelConstants.FORWARD_SLASH);
        super.addInterceptors(registry);
    }
}
