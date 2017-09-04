package com.sfsctech.configurer;

import com.sfsctech.auth.condition.SimpleAuthentication;
import com.sfsctech.auth.filter.SessionFilter;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
public class AuthConfigurer {

    @Autowired
    private WebMvcProperties properties;

    @Bean
    @Conditional(SimpleAuthentication.class)
    public FilterRegistrationBean SSOFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new SessionFilter());
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SessionFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.addInitParameter(SecurityConstants.FILTER_EXCLUDES_KEY, (StringUtils.isNotBlank(properties.getView().getSuffix()) ? LabelConstants.STAR + properties.getView().getSuffix() + LabelConstants.COMMA : "") + "/druid/*");
        return registration;
    }
}
