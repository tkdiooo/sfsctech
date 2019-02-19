package com.sfsctech.core.base.jwt;


import com.sfsctech.core.base.domain.dto.BaseDto;

/**
 * Class JwtToken
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
@Deprecated
public class JwtToken extends BaseDto {

    private static final long serialVersionUID = 2763237712905519322L;

    // jwt信息（根据盐值加密）
    private String jwt;
    // 加密盐值
    private String salt;
    // 加密盐值 - 缓存key（加密后）
    private String salt_CacheKey;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt_CacheKey() {
        return salt_CacheKey;
    }

    public void setSalt_CacheKey(String salt_CacheKey) {
        this.salt_CacheKey = salt_CacheKey;
    }

}
