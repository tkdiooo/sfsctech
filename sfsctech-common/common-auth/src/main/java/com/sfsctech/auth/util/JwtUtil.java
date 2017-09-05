package com.sfsctech.auth.util;

import com.sfsctech.common.util.DateUtil;
import com.sfsctech.auth.jwt.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtUtil {

    public static String generalJwt(JwtConfig config) {
        Key key = MacProvider.generateKey();//这里是加密解密的key。
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()//返回的字符串便是我们的jwt串了
                .setSubject("Joe")//设置主题
                .setIssuer("") // 发行方
                .setIssuedAt(DateUtil.getTimeMillisDate(nowMillis)) // 发行时间
                .setAudience("")// 接收方
                .setPayload("") // 承载内容
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
    public static Claims parseJWT(String compactJws) throws Exception {
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
