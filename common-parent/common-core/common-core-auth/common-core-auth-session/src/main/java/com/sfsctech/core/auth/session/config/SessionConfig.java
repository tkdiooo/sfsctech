package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.auth.base.config.AuthFilterConfig;
import com.sfsctech.core.auth.session.filter.SessionFilter;
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
@Import(AuthFilterConfig.class)
public class SessionConfig {

    @Autowired
    private AuthFilterConfig filterConfig;

    @Bean
    public FilterRegistrationBean SessionFilter() {
        SessionFilter filter = new SessionFilter();
        // Session认证排除路径
        filter.setExcludesPattern(filterConfig.getSessionExcludePath());
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SessionFilter");
        registration.setOrder(5);
        registration.addInitParameter("welcomeFile", filterConfig.getWelcomeFile());
        return registration;
    }

}
