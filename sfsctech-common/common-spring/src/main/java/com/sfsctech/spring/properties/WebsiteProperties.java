package com.sfsctech.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Class WebsiteProperties
 *
 * @author 张麒 2017/9/1.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website"
)
public class WebsiteProperties {

    private final WebsiteProperties.Support support;
    private final WebsiteProperties.Csrf csrf;
    private final WebsiteProperties.Session session;

    public WebsiteProperties() {
        this.support = new WebsiteProperties.Support();
        this.csrf = new WebsiteProperties.Csrf();
        this.session = new WebsiteProperties.Session();
    }

    public Support getSupport() {
        return support;
    }

    public Csrf getCsrf() {
        return csrf;
    }

    public Session getSession() {
        return session;
    }

    public static class Support {

        private String staticResources;
        private String welcomeFile;

        public Support() {
        }

        public String getStaticResources() {
            return staticResources;
        }

        public void setStaticResources(String staticResources) {
            this.staticResources = staticResources;
        }

        public String getWelcomeFile() {
            return welcomeFile;
        }

        public void setWelcomeFile(String welcomeFile) {
            this.welcomeFile = welcomeFile;
        }
    }

    public static class Csrf {
        private String keepPattern;
        private Set<String> interceptExcludePath;
        private Set<String> verifyExcludePath;

        public Csrf() {

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

    }

    public static class Session {
        private String authentication;
        private Set<String> excludePath;

        public Session() {
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public Set<String> getExcludePath() {
            return excludePath;
        }

        public void setExcludePath(Set<String> excludePath) {
            this.excludePath = excludePath;
        }
    }

}
