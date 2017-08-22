package com.sfsctech.common.cookie;

import com.sfsctech.constants.LabelConstants;

/**
 * Class CookiesConfig
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class Config {

    private String path = LabelConstants.FORWARD_SLASH;
    private String domain;
    private String comment;
    private int maxAge;
    private boolean secure;
    private boolean httpOnly;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
