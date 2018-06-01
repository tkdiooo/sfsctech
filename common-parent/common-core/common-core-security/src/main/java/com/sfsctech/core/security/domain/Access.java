package com.sfsctech.core.security.domain;

/**
 * Class Access
 *
 * @author 张麒 2018-5-30.
 * @version Description:
 */
public class Access {

    private String url;
    private String accessControlAllowMethods;
    private String accessControlAllowHeaders;
    private Boolean accessControlAllowCredentials;

    public Access() {

    }

    public Access(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessControlAllowMethods() {
        return accessControlAllowMethods;
    }

    public void setAccessControlAllowMethods(String accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    public String getAccessControlAllowHeaders() {
        return accessControlAllowHeaders;
    }

    public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    public Boolean getAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public void setAccessControlAllowCredentials(Boolean accessControlAllowCredentials) {
        this.accessControlAllowCredentials = accessControlAllowCredentials;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        return obj.toString().contains(this.url);
    }
}
