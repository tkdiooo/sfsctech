package com.sfsctech.auth.jwt;

import java.util.Map;

/**
 * Class Playload
 *
 * @author 张麒 2017/8/22.
 * @version Description:
 */
public class JwtConfig {

    // 主题
    private String subject;
    // 发行方
    private String issuer;
    // 接收方
    private String audience;
    // 保持时间
    private Long expiration;

    private Map<String, Object> claims;

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

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }
}
