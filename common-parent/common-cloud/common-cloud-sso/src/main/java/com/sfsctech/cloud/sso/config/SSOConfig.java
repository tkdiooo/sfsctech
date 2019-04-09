package com.sfsctech.cloud.sso.config;

import com.sfsctech.cloud.net.config.InterfaceProxyFactoryConfiguration;
import com.sfsctech.cloud.sso.provider.SSOProvider;
import com.sfsctech.core.auth.sso.base.inf.SSOInterface;
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
@Import({InterfaceProxyFactoryConfiguration.class, com.sfsctech.core.auth.sso.client.config.SSOConfig.class})
public class SSOConfig {

    @Bean
    public SSOInterface ssoInterface() {
        return new SSOProvider();
    }

}
