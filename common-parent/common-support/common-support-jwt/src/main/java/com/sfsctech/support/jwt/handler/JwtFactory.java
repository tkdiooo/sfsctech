package com.sfsctech.support.jwt.handler;

import com.auth0.jwt.JWT;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.DateUtil;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.jwt.constants.JwtConstants;
import com.sfsctech.support.jwt.model.JwtResult;
import com.sfsctech.support.jwt.properties.JwtProperties;
import com.sfsctech.support.jwt.proxy.JwtProxy;
import com.sfsctech.support.jwt.token.RawAccessJwt;
import com.sfsctech.support.jwt.token.RawRefreshJwt;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtFactory {

    private final Logger logger = LoggerFactory.getLogger(JwtFactory.class);

    private String appName;

    private JwtProperties settings;

    private CacheFactory<RedisProxy<String, Object>> factory;

    public JwtFactory() {

    }

    public JwtFactory(String appName, JwtProperties settings, CacheFactory<RedisProxy<String, Object>> factory) {
        this.appName = appName;
        this.settings = settings;
        this.factory = factory;
    }

    public JwtProperties getSettings() {
        return settings;
    }

    /**
     * 直接通过UserAuthData构建鉴权
     *
     * @param username 用户名
     * @return JwtResult
     */
    public JwtResult buildAuthorizeToken(String username) {
        if (StringUtil.isBlank(username))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

//        if (ListUtil.isEmpty(t.getAuthorities()))
//            throw new IllegalArgumentException("User doesn't have any privileges");
        return buildAuthorizeToken(buildJwtProxy(username));
    }

    /**
     * 通过JwtProxy类构建鉴权
     * 需要先获取jwt Claims对象，对登录用户session添加自定义信息操作时使用
     *
     * @param proxy JwtProxy
     * @return JwtResult
     */
    public JwtResult buildAuthorizeToken(JwtProxy proxy) {
        // 鉴权jwt
        String jwt = buildAccessJwt(proxy);
        logger.info("用户:{}，生成AccessJwt:{}", proxy.getClaims().getSubject(), jwt);
        // 生成缓存key（鉴权jwt）
        String access_Jwt_Cache = factory.buildCacheKey(appName, JwtConstants.CACHE_IDENTIFY_ACCESS_TOKEN, proxy.getClaims().getSubject(), DateUtil.toMSDateTimeDash(new Date()));
        logger.info("用户:{}，生成Access_Jwt_Cache:{}", proxy.getClaims().getSubject(), access_Jwt_Cache);
        // 缓存鉴权jwt
        factory.getCacheClient().putTimeOut(access_Jwt_Cache, jwt, (int) settings.getExpiration().getSeconds());
        // 加密鉴权jwt的缓存key，以作为token使用
        String access_Jwt_Token = EncrypterTool.encrypt(EncrypterTool.Security.AesCBC, access_Jwt_Cache);
        logger.info("用户:{}，生成Access_Jwt_Token(加密):{}", proxy.getClaims().getSubject(), access_Jwt_Token);
        // 刷新jwt
        String refreshJwt = buildRefreshJwt(proxy);
        logger.info("用户:{}，生成RefreshJwt:{}", proxy.getClaims().getSubject(), refreshJwt);
        // 加密鉴权jwt的缓存key，以作为token使用
        String refresh_Jwt_Token = EncrypterTool.encrypt(EncrypterTool.Security.AesCBC, refreshJwt);
        logger.info("用户:{}，生成Refresh_Jwt_Token(加密):{}", proxy.getClaims().getSubject(), refresh_Jwt_Token);
        return JwtResult.builder().accessToken(JwtConstants.TOKEN_PREFIX + access_Jwt_Token).refreshJwt(refresh_Jwt_Token).build();
    }

    /**
     * 获取RawAccessJwt
     *
     * @param jwt java web token
     * @return RawAccessJwt
     */
    public RawAccessJwt getRawAccessJwt(String jwt) {
        return new RawAccessJwt(parseJWT(jwt));
    }

    /**
     * 获取RawRefreshJwt
     *
     * @param jwt java web token
     * @return RawRefreshJwt
     */
    public RawRefreshJwt getRawRefreshJwt(String jwt) {
        String token = EncrypterTool.decrypt(EncrypterTool.Security.AesCBC, jwt);
        return new RawRefreshJwt(parseJWT(token));
    }

    /**
     * 解析jwt
     *
     * @param jwt java web token
     * @return Claims
     */
    public Jws<Claims> parseJWT(String jwt) throws UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, SignatureException, ExpiredJwtException {
        logger.info("开始解析jwt信息:{}", jwt);
        return Jwts.parser().requireIssuer(settings.getIssuer()).setSigningKey(getKey()).parseClaimsJws(jwt);
    }

    /**
     * 构建JwtProxy，通过getClaims()方法获取session属性集合
     *
     * @param username 用户名
     * @return JwtProxy
     */
    public JwtProxy buildJwtProxy(String username) {
        if (StringUtil.isBlank(username))
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        return JwtProxy.builder()
                .claims(Jwts.claims().setSubject(username))
                .issuer(settings.getIssuer())
                .expiration(settings.getExpiration())
                .refreshTime(settings.getRefreshTime())
                .key(getKey())
                .build();
    }

    /**
     * 签名key
     *
     * @return Key
     */
    private Key getKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(settings.getSalt());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    private String buildAccessJwt(JwtProxy proxy) {
        Date beginTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.now().plusMinutes(proxy.getExpiration().toMillis()).atZone(ZoneId.systemDefault()).toInstant());
        String jwt = buildJwt(proxy, beginTime, endTime);
        logger.info("生成AccessJwt信息：{主题:{},发行方:{},发行时间:{},过期时间:{}}", proxy.getClaims().getSubject(), proxy.getIssuer(), DateUtil.toDateTimeDash(beginTime), DateUtil.toDateTimeDash(endTime));
        return jwt;
    }

    private String buildRefreshJwt(JwtProxy proxy) {
        Date beginTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(LocalDateTime.now().plusMinutes(proxy.getRefreshTime().toMillis()).atZone(ZoneId.systemDefault()).toInstant());
        String jwt = buildJwt(proxy, beginTime, endTime);
        logger.info("生成RefreshJwt信息：{主题:{},发行方:{},发行时间:{},过期时间:{}}", proxy.getClaims().getSubject(), proxy.getIssuer(), DateUtil.toDateTimeDash(beginTime), DateUtil.toDateTimeDash(endTime));
        return jwt;
    }

    private String buildJwt(JwtProxy proxy, Date beginTime, Date endTime) {
        JWT.create().withIssuedAt(beginTime).withExpiresAt(endTime);
        return Jwts.builder()
                .setClaims(proxy.getClaims())
                .setIssuer(proxy.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(beginTime)
                .setExpiration(endTime)
                .signWith(SignatureAlgorithm.HS512, proxy.getKey())
                .compact();
    }
}
