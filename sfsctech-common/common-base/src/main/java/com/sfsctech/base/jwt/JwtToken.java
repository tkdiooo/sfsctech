package com.sfsctech.base.jwt;

import com.sfsctech.base.model.BaseDto;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Class JwtToken
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class JwtToken extends BaseDto {

    private static final long serialVersionUID = 2763237712905519322L;

    // jwt信息（根据盐值加密）
    @NotBlank
    private String jwt;
    // 加密盐值
    private String salt;
    // 加密盐值 - 缓存key（加密后）
    @NotBlank
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
