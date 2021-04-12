package com.sfsctech.support.svn.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Class AuthnRequestPropertyies
 *
 * @author 张麒 2019-12-3.
 * @version Description:
 */
@Component
@RefreshScope
@ConfigurationProperties(
        prefix = "svn"
)
public class SVNPropertyies {

    private String url;
    private String account;
    private String password;
    private String localPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
