package com.sfsctech.core.web.tools.cookie;


import com.sfsctech.core.base.constants.LabelConstants;

import java.time.Duration;

/**
 * Class CookiesConfig
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Config {

    private String name;
    private String domain;
    private String path = LabelConstants.FORWARD_SLASH;
    private String comment;
    private boolean httpOnly;
    private boolean secure;
    private Duration maxAge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean getSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public Duration getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Duration maxAge) {
        this.maxAge = maxAge;
    }
}
