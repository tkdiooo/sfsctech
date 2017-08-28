package com.sfsctech.security.jwt;

/**
 * Class JwtToken
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class JwtToken {

    // jwt信息（根据盐值加密）
    private String jwt;
    // 加密盐值 - 缓存key（加密后）
    private String salt_CacheKey;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getSalt_CacheKey() {
        return salt_CacheKey;
    }

    public void setSalt_CacheKey(String salt_CacheKey) {
        this.salt_CacheKey = salt_CacheKey;
    }
}
