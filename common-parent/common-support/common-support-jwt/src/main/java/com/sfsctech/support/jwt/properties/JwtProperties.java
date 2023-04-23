package com.sfsctech.support.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
    // token有效时间
    private Duration expiration = Duration.ofMinutes(30);
    // 主题
    private String subject;

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

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
