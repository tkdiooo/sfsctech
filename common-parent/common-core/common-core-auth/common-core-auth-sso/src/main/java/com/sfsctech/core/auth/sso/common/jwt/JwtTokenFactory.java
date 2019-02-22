package com.sfsctech.core.auth.sso.common.jwt;

import com.sfsctech.core.auth.sso.common.constants.SSOConstants;
import com.sfsctech.core.auth.sso.common.properties.JwtProperties;
import com.sfsctech.core.auth.sso.server.jwt.AccessJwtToken;
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
    public AccessJwtToken generalAccessJwt(User user) {
        if (StringUtil.isBlank(user.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (ListUtil.isEmpty(user.getAuthorities()))
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(SSOConstants.JWT_CLAIMS_SCOPES, user.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        AccessJwtToken jwtToken = AccessJwtToken.builder()
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

    // TODO
    public AccessJwtToken generalRefreshJwt(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        AccessJwtToken jwtToken = AccessJwtToken.builder()
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
     * @return Jws
     */
    public Jws<Claims> parseJWT(String jwt) {
        return Jwts.parser().requireIssuer(settings.getIssuer()).setSigningKey(getKey()).parseClaimsJws(jwt);
    }

}
