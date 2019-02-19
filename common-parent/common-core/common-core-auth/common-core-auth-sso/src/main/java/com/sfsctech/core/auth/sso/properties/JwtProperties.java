package com.sfsctech.core.auth.sso.properties;

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
    // 主题
    private String subject;
    // 发行方
    private String issuer;
    // 保持时间
    private Integer expiration = 1800000;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

}
