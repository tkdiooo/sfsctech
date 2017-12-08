package com.sfsctech.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

    private final SecurityProperties.Csrf csrf;
    private final SecurityProperties.Ddos ddos;

    public SecurityProperties() {
        this.csrf = new SecurityProperties.Csrf();
        this.ddos = new SecurityProperties.Ddos();
    }

    public Csrf getCsrf() {
        return csrf;
    }

    public Ddos getDdos() {
        return ddos;
    }

    public static class Csrf {
        private boolean open = false;
        private String keepPattern;
        private Set<String> interceptExcludePath;
        private Set<String> verifyExcludePath;
        private Set<String> requiredVerifyPath;

        Csrf() {

        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public String getKeepPattern() {
            return keepPattern;
        }

        public void setKeepPattern(String keepPattern) {
            this.keepPattern = keepPattern;
        }

        public Set<String> getInterceptExcludePath() {
            return interceptExcludePath;
        }

        public void setInterceptExcludePath(Set<String> interceptExcludePath) {
            this.interceptExcludePath = interceptExcludePath;
        }

        public Set<String> getVerifyExcludePath() {
            return verifyExcludePath;
        }

        public void setVerifyExcludePath(Set<String> verifyExcludePath) {
            this.verifyExcludePath = verifyExcludePath;
        }

        public Set<String> getRequiredVerifyPath() {
            return requiredVerifyPath;
        }

        public void setRequiredVerifyPath(Set<String> requiredVerifyPath) {
            this.requiredVerifyPath = requiredVerifyPath;
        }
    }

    public static class Ddos {
        private boolean open = false;
        private Set<String> accessControlAllowOrigin;

        Ddos() {

        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public Set<String> getAccessControlAllowOrigin() {
            return accessControlAllowOrigin;
        }

        public void setAccessControlAllowOrigin(Set<String> accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
        }
    }
}
