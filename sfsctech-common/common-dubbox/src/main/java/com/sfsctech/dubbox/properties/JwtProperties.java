package com.sfsctech.dubbox.properties;

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
        prefix = "website.jwt"
)
public class JwtProperties {
    // 主题
    private String subject;
    // 发行方
    private String issuer;
    // 保持时间
    private Long expiration;

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

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

}
