package com.sfsctech.core.security.properties;

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
    // 协议
    public enum KeepPattern {
        Session, Cache
    }

    private SecurityProperties.Ddos ddos;
    private SecurityProperties.Csrf csrf;

    public SecurityProperties() {
    }

    public Csrf getCsrf() {
        return csrf;
    }

    public void setCsrf(Csrf csrf) {
        this.csrf = csrf;
    }

    public Ddos getDdos() {
        return ddos;
    }


    public void setDdos(Ddos ddos) {
        this.ddos = ddos;
    }

    public static class Csrf {
        private KeepPattern keepPattern = KeepPattern.Session;
        private Set<String> interceptExcludePath;
        private Set<String> verifyExcludePath;
        private Set<String> requiredVerifyPath;

        public Csrf() {

        }

        public KeepPattern getKeepPattern() {
            return keepPattern;
        }

        public void setKeepPattern(KeepPattern keepPattern) {
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

        private Set<String> accessControlAllowOrigin;

        public Ddos() {

        }

        public Set<String> getAccessControlAllowOrigin() {
            return accessControlAllowOrigin;
        }

        public void setAccessControlAllowOrigin(Set<String> accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
        }
    }
}
