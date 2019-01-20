package com.sfsctech.core.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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
    private final WebsiteProperties.Session session;

    public WebsiteProperties() {
        this.support = new WebsiteProperties.Support();
        this.session = new WebsiteProperties.Session();
    }

    public Support getSupport() {
        return support;
    }

    public Session getSession() {
        return session;
    }

    public static class Support {

        private String staticResources;
        private String welcomeFile;
        private final Map<String, String> customConfig;

        public Support() {
            customConfig = new HashMap<>();
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

        public Map<String, String> getCustomConfig() {
            return customConfig;
        }
    }

    public static class Session {
        private Set<String> excludePath;

        Session() {
        }

        public Set<String> getExcludePath() {
            return excludePath;
        }

        public void setExcludePath(Set<String> excludePath) {
            this.excludePath = excludePath;
        }
    }

}
