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

    private Boolean openXss = true;
    private Boolean openCsrf = false;
    private final SecurityProperties.Csrf csrf;
    private Boolean openDdoc= false;
    private final SecurityProperties.Ddos ddos;

    public SecurityProperties() {
        this.csrf = new SecurityProperties.Csrf();
        this.ddos = new SecurityProperties.Ddos();
    }

    public Boolean getOpenXss() {
        return openXss;
    }

    public void setOpenXss(Boolean openXss) {
        this.openXss = openXss;
    }

    public Boolean getOpenCsrf() {
        return openCsrf;
    }

    public void setOpenCsrf(Boolean openCsrf) {
        this.openCsrf = openCsrf;
    }

    public Csrf getCsrf() {
        return csrf;
    }


    public Ddos getDdos() {
        return ddos;
    }

    public Boolean getOpenDdoc() {
        return openDdoc;
    }

    public void setOpenDdoc(Boolean openDdoc) {
        this.openDdoc = openDdoc;
    }

    public static class Csrf {
        private String keepPattern;
        private Set<String> interceptExcludePath;
        private Set<String> verifyExcludePath;
        private Set<String> requiredVerifyPath;

        Csrf() {

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
        private Set<String> accessControlAllowOrigin;

        Ddos() {

        }

        public Set<String> getAccessControlAllowOrigin() {
            return accessControlAllowOrigin;
        }

        public void setAccessControlAllowOrigin(Set<String> accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
        }
    }
}
