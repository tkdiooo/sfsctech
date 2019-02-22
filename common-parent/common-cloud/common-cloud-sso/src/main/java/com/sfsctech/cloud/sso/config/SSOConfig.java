package com.sfsctech.cloud.sso.config;

import com.sfsctech.core.auth.base.config.SkipPathConfig;
import com.sfsctech.core.auth.sso.common.properties.SSOProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import({SSOProperties.class, com.sfsctech.core.auth.sso.server.config.SSOConfig.class, SkipPathConfig.class})
public class SSOConfig {

    @Autowired
    private SkipPathConfig config;

//    @Bean
//    public FilterRegistrationBean SSOFilter() {
//        SSOFilter filter = new SSOFilter();
//        // Session认证排除路径
//        filter.setExcludesPattern(config.getExcludePath());
//        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
//        registration.setName("CloudSSOFilter");
//        registration.setOrder(5);
//        return registration;
//    }
}
