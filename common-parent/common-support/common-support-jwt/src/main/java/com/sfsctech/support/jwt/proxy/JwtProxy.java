package com.sfsctech.support.jwt.proxy;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;

import java.security.Key;
import java.time.Duration;

/**
 * Class JwtProxy
 *
 * @author 张麒 2019-7-16.
 * @version Description:
 */
@Getter
@Builder
public class JwtProxy {

    private Claims claims;
    private String issuer;
    private Duration expiration;
    private Duration refreshTime;
    private Key key;
}
