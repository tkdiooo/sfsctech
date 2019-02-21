package com.sfsctech.core.auth.sso.server.jwt;

import com.sfsctech.core.auth.sso.common.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.support.common.util.DateUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtTokenFactory {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenFactory.class);

    private JwtProperties settings;

    public JwtTokenFactory(JwtProperties settings) {
        this.settings = settings;
    }

    public JwtProperties getSettings() {
        return settings;
    }

    private Key getKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(settings.getSalt());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    /**
     * 生成Jwt信息
     *
     * @param user User
     * @return JwtToken
     */
    public JwtToken generalAccessJwt(User user) {
        if (StringUtil.isBlank(user.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (ListUtil.isEmpty(user.getAuthorities()))
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(SSOConstants.JWT_CLAIMS_SCOPES, user.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        JwtToken jwtToken = JwtToken.builder()
                .beginDate(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .endDate(Date.from(currentTime.plusSeconds(settings.getExpiration()).atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getIssuer())
                .setIssuedAt(jwtToken.getBeginDate())
                .setExpiration(jwtToken.getEndDate())
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
        logger.info("生成AccessJwt信息：{主题:{},发行方:{},发行时间:{},过期时间:{}}", user.getUsername(), settings.getIssuer(), DateUtil.toDateTimeDash(jwtToken.getBeginDate()), DateUtil.toDateTimeDash(jwtToken.getEndDate()));
        jwtToken.setJwt(jwt);
        return jwtToken;
    }

    public JwtToken generalRefreshJwt(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        JwtToken jwtToken = JwtToken.builder()
                .beginDate(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .endDate(Date.from(currentTime.plusSeconds(settings.getRefreshTime()).atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("scopes", Collections.singletonList(SSOConstants.Scopes.REFRESH_TOKEN.authority()));

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getRefreshTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
        logger.info("生成RefreshJwt信息：{主题:{},发行方:{},发行时间:{},过期时间:{}}", user.getUsername(), settings.getIssuer(), DateUtil.toDateTimeDash(jwtToken.getBeginDate()), DateUtil.toDateTimeDash(jwtToken.getEndDate()));
        jwtToken.setJwt(jwt);

        return jwtToken;
    }

    /**
     * 解密jwt
     *
     * @param jwt java web token
     * @return Claims
     */
    public Claims parseJWT(String jwt) {
        try {
            return Jwts.parser()
                    .requireIssuer(settings.getIssuer())
                    .setSigningKey(getKey()).parseClaimsJws(jwt).getBody();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | MissingClaimException | IncorrectClaimException e) {
            // 签名(Signature)验证失败
            if (e instanceof SignatureException) {
                throw new JwtException("Jwt验证错误：[签名(Signature)验证失败]", e);
            }
            // jwt 解析错误
            else if (e instanceof MalformedJwtException) {
                throw new JwtException("Jwt验证错误：[jwt解析错误]", e);
            }
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常。
            else if (e instanceof ExpiredJwtException) {
                throw new JwtException("Jwt验证错误：[jwt已经过期]", e);
            }
            // 需要的声明不存在
            else if (e instanceof MissingClaimException) {
                throw new JwtException("Jwt验证错误：[Claims需要的声明不存在]", e);
            }
            // 载荷(Payload) 有错误
            else {
                throw new JwtException("Jwt验证错误：[载荷(Payload) 有错误]", e);
            }
        }
    }

}
