package com.sfsctech.configurer;

import com.sfsctech.constants.LabelConstants;
import com.sfsctech.dubbox.condition.SSOCondition;
import com.sfsctech.dubbox.filter.SSOFilter;
import com.sfsctech.dubbox.properties.JwtProperties;
import com.sfsctech.dubbox.properties.SSOProperties;
import com.sfsctech.spring.properties.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Class SSOConfigurer
 *
 * @author 张麒 2017/10/10.
 * @version Description:
 */
@Configuration
@Conditional(SSOCondition.class)
@ComponentScan(basePackageClasses = {SSOProperties.class, JwtProperties.class, AppConfig.class})
public class SSOConfigurer {

    @Autowired
    private AppConfig appConfig;

    @Bean
    @Conditional(SSOCondition.class)
    public FilterRegistrationBean SSOFilter() {
        SSOFilter filter = new SSOFilter();
        // Session认证排除路径
        filter.setExcludesPattern(appConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SSOFilter");
        return registration;
    }
}
