package com.sfsctech.core.auth.session.config;

import com.sfsctech.core.auth.base.config.BaseWebSecurityConfig;
import com.sfsctech.core.auth.base.point.LoginUrlAuthenticationEntryPoint;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.auth.base.config.AuthFilterConfig;
import com.sfsctech.core.auth.session.filter.SessionFilter;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Class AuthConfigurer
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Configuration
@Import(AuthFilterConfig.class)
public class SessionConfig extends BaseWebSecurityConfig {

    @Autowired
    private AuthFilterConfig filterConfig;

    @Bean
    public FilterRegistrationBean SessionFilter() {
        SessionFilter filter = new SessionFilter();
        // Session认证排除路径
        filter.setExcludesPattern(filterConfig.getSessionExcludePath());
        FilterRegistrationBean<SessionFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns(LabelConstants.SLASH_STAR);
        registration.setName("SessionFilter");
        registration.setOrder(5);
        registration.addInitParameter("welcomeFile", filterConfig.getWelcomeFile());
        return registration;
    }
}
