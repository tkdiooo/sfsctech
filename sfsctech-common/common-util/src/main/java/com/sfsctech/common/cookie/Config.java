package com.sfsctech.common.cookie;

import com.sfsctech.constants.LabelConstants;

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
    private Boolean httpOnly;
    private Boolean secure;
    private Integer maxAge;

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

    public Boolean getHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
