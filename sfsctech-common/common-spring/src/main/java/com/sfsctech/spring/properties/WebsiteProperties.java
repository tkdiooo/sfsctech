package com.sfsctech.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final WebsiteProperties.Request request;
    private final WebsiteProperties.Session session;

    public WebsiteProperties() {
        this.support = new WebsiteProperties.Support();
        this.request = new WebsiteProperties.Request();
        this.session = new WebsiteProperties.Session();
    }

    public Support getSupport() {
        return support;
    }

    public Request getRequest() {
        return request;
    }

    public Session getSession() {
        return session;
    }

    public static class Support {

        private String staticResources;

        public Support() {
        }

        public String getStaticResources() {
            return staticResources;
        }

        public void setStaticResources(String staticResources) {
            this.staticResources = staticResources;
        }
    }

    public static class Request {
        private List<String> filterExclude;
        private List<String> csrfExclude;

        public Request() {
        }

        public List<String> getFilterExclude() {
            return filterExclude;
        }

        public void setFilterExclude(List<String> filterExclude) {
            this.filterExclude = filterExclude;
        }

        public List<String> getCsrfExclude() {
            return csrfExclude;
        }

        public void setCsrfExclude(List<String> csrfExclude) {
            this.csrfExclude = csrfExclude;
        }
    }

    public static class Session {
        private String authentication;

        public Session() {
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }
    }
}
