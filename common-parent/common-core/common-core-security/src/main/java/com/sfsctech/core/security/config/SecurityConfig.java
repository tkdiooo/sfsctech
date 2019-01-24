package com.sfsctech.core.security.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.controller.GlobalErrorController;
import com.sfsctech.core.exception.handler.GlobalExceptionHandler;
import com.sfsctech.core.security.factory.HandlerMethodFactory;
import com.sfsctech.core.security.filter.CORSFilter;
import com.sfsctech.core.security.filter.DDOCFilter;
import com.sfsctech.core.security.filter.XSSFilter;
import com.sfsctech.core.security.interceptor.HttpRequestSecurityInterceptor;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.security.resolver.RequestAttributeResolver;
import com.sfsctech.support.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SecurityConfigurer
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
@Configuration
@Import({SecurityProperties.class, WebSecurityConfig.class, GlobalErrorController.class, GlobalExceptionHandler.class, CacheConfig.class, WebSecurityConfig.class})
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    /**
     * 自定义参数解析器
     *
     * @param resolvers HandlerMethodArgumentResolver
     */
    @Override
    public void addArgumentResolvers(@Nullable @NotNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestAttributeResolver());
    }

    /**
     * 访问安全拦截器
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(@Nullable @NotNull InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        registry.addInterceptor(new HttpRequestSecurityInterceptor())
                // addPathPatterns 用于添加拦截规则
                .addPathPatterns(LabelConstants.SLASH_DOUBLE_STAR)
                // excludePathPatterns 用户拦截排除
                .excludePathPatterns(FilterHandler.getCSRFExcludes())
                .excludePathPatterns(new ArrayList<>(properties.http().getInterceptExcludePath()));
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
        FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<>(new XSSFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("XSSFilter");
        registration.setOrder(XSSFilter.FILTER_ORDER);
        return registration;
    }

    /**
     * 跨域访问过滤
     */
    @Bean
    @ConditionalOnProperty(name = "website.security.cors.enabled", havingValue = "true")
    public FilterRegistrationBean CORSFilter() {
        if (properties.cors().getCrossDomain() == null) {
            throw new RuntimeException("website.security.cors 跨域请求已经激活，跨域访问路径不能为空，请设置website.security.cors.cross-domain");
        }
        FilterRegistrationBean<CORSFilter> registration = new FilterRegistrationBean<>(new CORSFilter(MapUtil.toMap(properties.cors().getCrossDomain(), "url")));
        registration.setName("CORSFilter");
        registration.setOrder(CORSFilter.FILTER_ORDER);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        return registration;
    }

    /**
     * DDOC过滤
     */
    @Bean
    @ConditionalOnProperty(name = "website.security.ddos.enabled", havingValue = "true")
    public FilterRegistrationBean DDOCFilter() {
        DDOCFilter filter = new DDOCFilter(properties.ddos(), factory.getCacheClient().getRedisTemplate());
        FilterRegistrationBean<DDOCFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("DDOCFilter");
        registration.setOrder(DDOCFilter.FILTER_ORDER);
        return registration;
    }
}
