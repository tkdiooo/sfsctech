package com.sfsctech.dubbox.util;

import com.sfsctech.common.util.DateUtil;
import com.sfsctech.dubbox.properties.JwtProperties;
import com.sfsctech.common.util.SpringContextUtil;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtUtil {

    private static final String salt = "08ud7g974Gw5f54skr21w43Jw3wqW08247EH76z";

    private static JwtProperties config = SpringContextUtil.getBean(JwtProperties.class);

    private static Key getKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public static String generalJwt(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        //返回的字符串便是我们的jwt串了
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims) // 设置内容
                .setSubject(config.getSubject()) // 设置主题
                .setIssuer(config.getIssuer()) // 发行方
                .setIssuedAt(DateUtil.getTimeMillisDate(nowMillis)) // 发行时间
                .signWith(SignatureAlgorithm.HS512, getKey());
        // 过期时间
        if (null != config.getExpiration() && config.getExpiration() >= 0) {
            builder.setExpiration(DateUtil.getTimeMillisDate(config.getExpiration() + nowMillis));
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public static Claims parseJWT(String jwt) {
        try {
            return Jwts.parser()
                    .requireSubject(config.getSubject())
                    .requireIssuer(config.getIssuer())
                    .setSigningKey(getKey()).parseClaimsJws(jwt).getBody();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | MissingClaimException | IncorrectClaimException e) {
            // 签名(Signature)验证失败
            if (e instanceof SignatureException) {
                e.printStackTrace();
            }
            // jwt 解析错误
            else if (e instanceof MalformedJwtException) {
                e.printStackTrace();
            }
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常。
            else if (e instanceof ExpiredJwtException) {
                e.printStackTrace();
            }
            // 需要的声明不存在
            else if (e instanceof MissingClaimException) {
                e.printStackTrace();
            }
            // 载荷(Payload) 有错误
            else if (e instanceof IncorrectClaimException) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
