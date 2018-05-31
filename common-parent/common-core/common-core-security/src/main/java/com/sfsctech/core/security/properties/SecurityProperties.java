package com.sfsctech.core.security.properties;

import com.sfsctech.core.security.domain.Access;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
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

    private DDOS ddos;
    private CSRF csrf;
    private Map<String, Access> crossDomain;

    public SecurityProperties() {
    }

    public CSRF getCSRF() {
        return csrf;
    }

    public void setCsrf(CSRF csrf) {
        this.csrf = csrf;
    }

    public DDOS getDDOS() {
        return ddos;
    }

    public void setDdos(DDOS ddos) {
        this.ddos = ddos;
    }

    public Map<String, Access> getCrossDomain() {
        return crossDomain;
    }

    public void setCrossDomain(Map<String, Access> crossDomain) {
        this.crossDomain = crossDomain;
    }

    public static class CSRF {
        private boolean enabled = false;
        private KeepPattern keepPattern = KeepPattern.Session;
        private Set<String> interceptExcludePath;
        private Set<String> verifyExcludePath;
        private Set<String> requiredVerifyPath;

        public CSRF() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
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

    public static class DDOS {

        private boolean enabled = false;

        private Set<String> whitelist;

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

        public Set<String> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(Set<String> whitelist) {
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
}
