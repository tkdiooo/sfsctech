package com.sfsctech.core.security.properties;

import com.sfsctech.core.security.domain.Access;
import com.sfsctech.core.security.domain.Whitelist;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.List;
import java.util.Set;

/**
 * Class SecurityProperties
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website.security"
)
public class SecurityProperties {

    private DDOS ddos;
    private CORS cors;
    private Http http;
    private Authentication authentication;

    public SecurityProperties() {
    }

    public DDOS ddos() {
        return ddos;
    }

    public void setDDOS(DDOS ddos) {
        this.ddos = ddos;
    }

    public CORS cors() {
        return cors;
    }

    public void setCORS(CORS cors) {
        this.cors = cors;
    }

    public Authentication authentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Http http() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public static class Http {
        private boolean disable = true;
        private Set<String> interceptExcludePath;

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }

        public Set<String> getInterceptExcludePath() {
            return interceptExcludePath;
        }

        public void setInterceptExcludePath(Set<String> interceptExcludePath) {
            this.interceptExcludePath = interceptExcludePath;
        }

    }

    public static class Authentication {

        // 认证模式
        public enum Pattern {
            None, Custom
        }

        private Pattern pattern = Pattern.None;
        private Class<UserDetailsService> userDetailsService = null;
        private Class<PasswordEncoder> passwordEncoder = null;
        private Class<AuthenticationSuccessHandler> authenticationSuccessHandler = null;
        private Class<Filter> authenticationFilter = null;

        public Authentication() {

        }

        public Pattern getPattern() {
            return pattern;
        }

        public void setPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        public Class<UserDetailsService> getUserDetailsService() {
            return userDetailsService;
        }

        public void setUserDetailsService(Class<UserDetailsService> userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        public Class<PasswordEncoder> getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(Class<PasswordEncoder> passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public Class<AuthenticationSuccessHandler> getAuthenticationSuccessHandler() {
            return authenticationSuccessHandler;
        }

        public void setAuthenticationSuccessHandler(Class<AuthenticationSuccessHandler> authenticationSuccessHandler) {
            this.authenticationSuccessHandler = authenticationSuccessHandler;
        }
    }

    public static class DDOS {
        private boolean enabled = false;
        private List<Whitelist> whitelist;
        private int timeSpan;
        private int limit;

        public DDOS() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<Whitelist> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(List<Whitelist> whitelist) {
            this.whitelist = whitelist;
        }

        public int getTimeSpan() {
            return timeSpan;
        }

        public void setTimeSpan(int timeSpan) {
            this.timeSpan = timeSpan;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

    public static class CORS {
        private boolean enabled = false;
        private List<Access> crossDomain;

        public CORS() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<Access> getCrossDomain() {
            return crossDomain;
        }

        public void setCrossDomain(List<Access> crossDomain) {
            this.crossDomain = crossDomain;
        }
    }
}
