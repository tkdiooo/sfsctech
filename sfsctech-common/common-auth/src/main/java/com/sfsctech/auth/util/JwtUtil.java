package com.sfsctech.auth.util;

import com.sfsctech.common.util.DateUtil;
import com.sfsctech.auth.jwt.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.HashMap;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtUtil {

    public static String generalJwt(JwtConfig config) {
        //这里是加密解密的key。
        Key key = MacProvider.generateKey();
        long nowMillis = System.currentTimeMillis();

        //返回的字符串便是我们的jwt串了
        JwtBuilder builder = Jwts.builder().setClaims(config.getClaims())
                .setSubject(config.getSubject())//设置主题
                .setIssuer(config.getIssuer()) // 发行方
                .setIssuedAt(DateUtil.getTimeMillisDate(nowMillis)) // 发行时间
                .setAudience(config.getAudience())// 接收方
                .signWith(SignatureAlgorithm.HS512, key);
        // 过期时间
        if (null != config.getExpiration() && config.getExpiration() >= 0) {
            builder.setExpiration(DateUtil.getTimeMillisDate(config.getExpiration() + nowMillis));
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public static Claims parseJWT(String compactJws) {
        try {
            return Jwts.parser().setSigningKey(MacProvider.generateKey()).parseClaimsJws(compactJws).getBody();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException e) {
            if (e instanceof SignatureException) {

            } else if (e instanceof MalformedJwtException) {

            } else {
            }
            return null;
        }
    }
}
