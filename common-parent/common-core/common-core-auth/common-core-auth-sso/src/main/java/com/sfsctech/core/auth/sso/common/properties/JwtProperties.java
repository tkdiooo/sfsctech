package com.sfsctech.core.auth.sso.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class Playload
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website.sso.jwt"
)
public class JwtProperties {
    // 盐值
    private String salt = "08ud7g974Gw5f54skr21w43Jw3wqW08247EH76z";
    // 发行方
    private String issuer;
    // 保持时间
    private Integer expiration = 1800000;
    // 刷新token保存时间
    private Integer refreshTime = 3600000;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public Integer getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Integer refreshTime) {
        this.refreshTime = refreshTime;
    }
}
