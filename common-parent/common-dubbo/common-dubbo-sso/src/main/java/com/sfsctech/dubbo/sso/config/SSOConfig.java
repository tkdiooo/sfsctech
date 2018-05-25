package com.sfsctech.dubbo.sso.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sfsctech.core.auth.base.config.AuthFilterConfig;
import com.sfsctech.core.auth.sso.inf.VerifyService;
import com.sfsctech.core.auth.sso.properties.AuthConfig;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.dubbo.base.config.DubboxConfig;
import com.sfsctech.dubbo.sso.filter.SSOFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import({AuthConfig.class, AuthFilterConfig.class, DubboxConfig.class, SSOProperties.class, JwtProperties.class})
public class SSOConfig {

    @Autowired
    private AuthFilterConfig filterConfig;
    @Autowired
    private SSOProperties ssoProperties;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @Bean
    public FilterRegistrationBean SSOFilter() {
        SSOFilter filter = new SSOFilter();
        // Session认证排除路径
        filter.setExcludesPattern(filterConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        registration.setOrder(5);
        return registration;
    }

    @Bean
    public ReferenceConfig<VerifyService> referenceConfig() {
        ReferenceConfig<VerifyService> config = new ReferenceConfig<>();
        config.setInterface(VerifyService.class);
        config.setId(VerifyService.class.getSimpleName());
        config.setLazy(ssoProperties.getReference().isLazy());
        config.setInit(true);
        config.setVersion(ssoProperties.getReference().getVersion());
        config.setTimeout(ssoProperties.getReference().getTimeout());
        config.setApplication(applicationConfig);
        config.setRegistry(registryConfig);
//        List<MethodConfig> methods = new ArrayList<>();
//        MethodConfig methodConfig = new MethodConfig();
//        methodConfig.setAsync(true);
//        methodConfig.setName("updateSession");
//        methods.add(methodConfig);
//        config.setMethods(methods);
        return config;
    }
}
