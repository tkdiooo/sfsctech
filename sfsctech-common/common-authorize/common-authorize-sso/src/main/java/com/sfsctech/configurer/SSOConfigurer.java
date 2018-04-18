package com.sfsctech.configurer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sfsctech.authorize.base.inf.VerifyService;
import com.sfsctech.authorize.base.properties.JwtProperties;
import com.sfsctech.authorize.sso.filter.SSOFilter;
import com.sfsctech.authorize.sso.properties.AuthConfig;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.authorize.base.properties.SSOProperties;
import com.sfsctech.spring.properties.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@ComponentScan(basePackageClasses = {AppConfig.class, SSOProperties.class, JwtProperties.class})
@EnableConfigurationProperties(AuthConfig.class)
public class SSOConfigurer {

    @Autowired
    private AppConfig appConfig;
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
        filter.setExcludesPattern(appConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
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
