package com.sfsctech.core.auth.sso.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.auth.base.config.AuthConfig;
import com.sfsctech.core.auth.sso.filter.SSOFilter;
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
@Import(AuthConfig.class)
public class SSOConfig {

    @Autowired
    private AuthConfig authConfig;
//    @Autowired
//    private SSOProperties ssoProperties;
//    @Autowired
//    private ApplicationConfig applicationConfig;
//    @Autowired
//    private RegistryConfig registryConfig;

    @Bean
    public FilterRegistrationBean SSOFilter() {
        SSOFilter filter = new SSOFilter();
        // Session认证排除路径
        filter.setExcludesPattern(authConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        return registration;
    }

    // TODO 迁移至dubbox
//    @Bean
//    public ReferenceConfig<VerifyService> referenceConfig() {
//        ReferenceConfig<VerifyService> config = new ReferenceConfig<>();
//        config.setInterface(VerifyService.class);
//        config.setId(VerifyService.class.getSimpleName());
//        config.setLazy(ssoProperties.getReference().isLazy());
//        config.setInit(true);
//        config.setVersion(ssoProperties.getReference().getVersion());
//        config.setTimeout(ssoProperties.getReference().getTimeout());
//        config.setApplication(applicationConfig);
//        config.setRegistry(registryConfig);
////        List<MethodConfig> methods = new ArrayList<>();
////        MethodConfig methodConfig = new MethodConfig();
////        methodConfig.setAsync(true);
////        methodConfig.setName("updateSession");
////        methods.add(methodConfig);
////        config.setMethods(methods);
//        return config;
//    }
}
