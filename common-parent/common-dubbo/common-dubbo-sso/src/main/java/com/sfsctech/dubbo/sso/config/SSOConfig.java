package com.sfsctech.dubbo.sso.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sfsctech.core.auth.base.config.AuthConfig;
import com.sfsctech.dubbo.base.inf.VerifyService;
import com.sfsctech.dubbo.base.config.DubboxConfig;
import com.sfsctech.dubbo.sso.properties.SSOProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
@Import({SSOProperties.class, com.sfsctech.core.auth.sso.config.SSOConfig.class, AuthConfig.class, DubboxConfig.class})
public class SSOConfig {

    @Autowired
    private SSOProperties ssoProperties;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @Bean
    public ReferenceConfig<VerifyService> referenceConfig() {
        ReferenceConfig<VerifyService> config = new ReferenceConfig<>();
        config.setInterface(VerifyService.class);
        config.setId(VerifyService.class.getSimpleName());
        config.setLazy(ssoProperties.isLazy());
        config.setInit(true);
        config.setVersion(ssoProperties.getVersion());
        config.setTimeout(ssoProperties.getTimeout());
        config.setApplication(applicationConfig);
        config.setRegistry(registryConfig);
        config.setUrl(ssoProperties.getUrl());
//        List<MethodConfig> methods = new ArrayList<>();
//        MethodConfig methodConfig = new MethodConfig();
//        methodConfig.setAsync(true);
//        methodConfig.setName("updateSession");
//        methods.add(methodConfig);
//        config.setMethods(methods);
        return config;
    }
}
