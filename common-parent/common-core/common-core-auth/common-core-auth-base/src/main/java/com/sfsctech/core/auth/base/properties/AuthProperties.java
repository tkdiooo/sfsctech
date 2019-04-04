package com.sfsctech.core.auth.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Set;

/**
 * Class SecurityProperties
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website.auth"
)
public class AuthProperties {

    /**
     * Session保持类型
     */
    public enum SessionKeep {
        Cookie, Header
    }

    private boolean disable = true;
    // 排除路径
    private Set<String> excludePath;
    private SessionKeep sessionKeep = SessionKeep.Cookie;
    // 登录设置
    private final Login login;
    // 登出设置
    private final Logout logout;
    // 权限设置
    private final Permit permit;

    public AuthProperties() {
        this.login = new Login();
        this.logout = new Logout();
        this.permit = new Permit();
    }

    public static class Login {
        // 登录url
        private String url = "/login";
        // 登录请求页面协议：https or http
        private boolean https = false;
        // 登录页面跳转模式：forward or redirect
        private boolean useForward = false;
        // 用户登录校验
        private Class<UserDetailsService> userDetailsService = null;
        // 用户密码校验
        private Class<PasswordEncoder> passwordEncoder = null;
        // 登录成功处理
        private Class<AuthenticationSuccessHandler> authenticationSuccessHandler = null;
        // 登录失败处理
        private Class<AuthenticationFailureHandler> authenticationFailureHandler = null;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHttps() {
            return https;
        }

        public void setHttps(boolean https) {
            this.https = https;
        }

        public boolean isUseForward() {
            return useForward;
        }

        public void setUseForward(boolean useForward) {
            this.useForward = useForward;
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

        public Class<AuthenticationFailureHandler> getAuthenticationFailureHandler() {
            return authenticationFailureHandler;
        }

        public void setAuthenticationFailureHandler(Class<AuthenticationFailureHandler> authenticationFailureHandler) {
            this.authenticationFailureHandler = authenticationFailureHandler;
        }
    }

    public static class Logout {
        // 登出路径
        private String url = "/logout";

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Permit {
        // 请求权限校验
        private Class<Filter> authorityFilter = null;
        // 403响应处理
        private Class<AccessDeniedHandler> accessDeniedHandler = null;

        public Class<AccessDeniedHandler> getAccessDeniedHandler() {
            return accessDeniedHandler;
        }

        public void setAccessDeniedHandler(Class<AccessDeniedHandler> accessDeniedHandler) {
            this.accessDeniedHandler = accessDeniedHandler;
        }

        public Class<Filter> getAuthorityFilter() {
            return authorityFilter;
        }

        public void setAuthorityFilter(Class<Filter> authorityFilter) {
            this.authorityFilter = authorityFilter;
        }
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Set<String> getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(Set<String> excludePath) {
        this.excludePath = excludePath;
    }

    public SessionKeep getSessionKeep() {
        return sessionKeep;
    }

    public void setSessionKeep(SessionKeep sessionKeep) {
        this.sessionKeep = sessionKeep;
    }

    public Login getLogin() {
        return login;
    }

    public Logout getLogout() {
        return logout;
    }

    public Permit getPermit() {
        return permit;
    }

}
