package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.common.util.DateUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtUtil {

    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String salt = "08ud7g974Gw5f54skr21w43Jw3wqW08247EH76z";

    @Autowired
    private JwtProperties config;

    private Key getKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(salt);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 生成Jwt信息
     *
     * @param claims Map
     * @return java web token
     */
    public String generalJwt(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date begin = DateUtil.getTimeMillisDate(nowMillis);
        Date end = DateUtil.getTimeMillisDate(config.getExpiration() + nowMillis);
        //返回的字符串便是我们的jwt串了
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims) // 设置内容
                .setSubject(config.getSubject()) // 设置主题
                .setIssuer(config.getIssuer()) // 发行方
                .setIssuedAt(begin) // 发行时间
                .setExpiration(end)
                .signWith(SignatureAlgorithm.HS512, getKey());
        // 过期时间
//        if (null != config.getExpiration() && config.getExpiration() >= 0) {
//        builder.setExpiration(DateUtil.getTimeMillisDate(config.getExpiration() + nowMillis));
//        }
        logger.info("生成Jwt信息：{主题:{},发行方:{},发行时间:{},过期时间:{}}", config.getSubject(), config.getIssuer(), DateUtil.toDateTimeDash(begin), DateUtil.toDateTimeDash(end));
        return builder.compact();
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
                    .requireSubject(config.getSubject())
                    .requireIssuer(config.getIssuer())
                    .setSigningKey(getKey()).parseClaimsJws(jwt).getBody();
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | MissingClaimException | IncorrectClaimException e) {
            // 签名(Signature)验证失败
            if (e instanceof SignatureException) {
                throw new JwtException("Jwt验证错误：[签名(Signature)验证失败]");
            }
            // jwt 解析错误
            else if (e instanceof MalformedJwtException) {
                throw new JwtException("Jwt验证错误：[jwt解析错误]");
            }
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常。
            else if (e instanceof ExpiredJwtException) {
                throw new JwtException("Jwt验证错误：[jwt已经过期]");
            }
            // 需要的声明不存在
            else if (e instanceof MissingClaimException) {
                throw new JwtException("Jwt验证错误：[Claims需要的声明不存在]");
            }
            // 载荷(Payload) 有错误
            else {
                throw new JwtException("Jwt验证错误：[载荷(Payload) 有错误]");
            }
        }
    }

    public JwtProperties getConfig() {
        return config;
    }

//    public void setConfig(JwtProperties config) {
//        this.config = config;
//    }

    public static void main(String[] args) {
//        JwtProperties properties = new JwtProperties();
//        properties.setExpiration(1800 * 1000L);
//        properties.setIssuer("testIssuer");
//        properties.setSubject("testSubject");
//        JwtUtil jwtUtil = new JwtUtil();
//        jwtUtil.setConfig(properties);
//        Claims claims = new DefaultClaims();
//        String jwt = jwtUtil.generalJwt(claims);
//        System.out.println(jwt);
//        while (true){
//            System.out.println(jwtUtil.parseJWT("eyJhbGciOiJIUzUxMiJ9.eyJqd3RfdXNlcl9hdXRoX2luZm8iOnsiaWQiOjM0MSwiZ3VpZCI6IlZzTXB0R2lmbnJWa0VlcnRRZUtod2YiLCJwYXJlbnRHdWlkIjpudWxsLCJsb2dpbk5hbWUiOiJmYmFvNSIsInVzZXJTaG9ydG5hbWUiOm51bGwsInBhc3N3b3JkIjoiMjljOWQ1ZjhhZjBkZTgyZjUzNDI0YzY4NWJmYWVlOWIiLCJyZWdpc3Rlck1vYmlsZSI6IjEzMjQzNTQ1NDU2IiwicmVnaXN0ZXJFbWFpbCI6ImRzZnNmQHFxLmNvbSIsInVzZXJUeXBlIjoiMiIsImFjY291bnRUeXBlIjoiMSIsImFjY291bnRMZXZlbCI6IjAiLCJpc1ZhbGlkIjoiMSIsImNyZWF0ZVBlcnNvbiI6ImZiYW81IiwiY3JlYXRlRGF0ZSI6MTU0NDk3NjAwMDAwMCwibW9kaWZ5UGVyc29uIjpudWxsLCJtb2RpZnlEYXRlIjoxNTUxNzE1MjAwMDAwLCJ1c2VySW5mb0lkIjoxMTA2LCJsYXN0TG9naW5UaW1lIjoxNTUxNzY3MzI4MDAwLCJpbnZhbGlkUmVhc29uIjpudWxsLCJpbnZhbGlkRGF0ZSI6bnVsbCwiaW52YWxpZFBlcnNvbiI6bnVsbCwidXNlckluZm9EdG8iOnsidXNlckluZm9JZCI6MTEwNiwidXNlck5hbWUiOiLlj5HljIXotKblj7c1IiwidXNlclNob3J0bmFtZSI6IuWPkeWMhTUiLCJ1c2VyTm8iOiIxMTAwNSIsImVudGVycHJpc2VOYXR1cmUiOjEsImJ1c2luZXNzUmVxdWlyZW1lbnQiOm51bGwsInVzZXJUeXBlIjoiMiIsInN1cHBsaWVyVHlwZSI6MSwidXNlclNvdXJjZSI6bnVsbCwiY291bnRyeU5vIjoiQ0giLCJjaXR5Tm8iOjExMDEwMCwicmVnaXN0ZXJlZENhcGl0YWwiOm51bGwsImN1cnJlbmN5Tm8iOm51bGwsInNvY2lhbENyZWRpdENvZGUiOiIxMTIyMzM0NDU1NjY3Nzg4OTkiLCJidXNpbmVzc0xpY2Vuc2VCZWdpbkRhdGUiOjYzMTEyMzIwMDAwMCwiYnVzaW5lc3NMaWNlbnNlRW5kRGF0ZSI6MjUzNDAyMTg1NjAwMDAwLCJvcGVuaW5nQmFua05hbWUiOm51bGwsIm9wZW5pbmdCYW5rQWNjb3VudCI6bnVsbCwidGF4cGF5ZXJJZGVudGlmeU51bWJlciI6bnVsbCwiaXNHZW5lcmFsVGF4cGF5ZXIiOjEsImFkZHJlc3MiOiLmmK_lkKbmt7FWMSIsInppcCI6bnVsbCwidGVsZXBob25lIjoiMTMyMzU0Njc1NzQiLCJmYXgiOm51bGwsIndlYnNpdGVVcmwiOm51bGwsImlzRnNnIjoiMCIsImlzU3ViQ29tcCI6IjAiLCJpc1dlYiI6IjEiLCJpc1ZhbGlkIjoiMSIsIm1lbW8iOm51bGwsImNyZWF0ZVBlcnNvbiI6ImZiYW81IiwiY3JlYXRlRGF0ZSI6MTU0NDk3NjAwMDAwMCwibW9kaWZ5UGVyc29uIjpudWxsLCJtb2RpZnlEYXRlIjpudWxsLCJmcm96ZW5SZWFzb24iOm51bGwsImZyb3plbkRhdGUiOm51bGwsImNhQ3VzdG9tZXJJZCI6IjgxQTAwNkI4NDE2NUExNkRGMzgzRDg3QjIzQTNBNzM2IiwiY2FHZW5lcmF0ZVRpbWUiOjE1NTExMTA0MDAwMDAsImNvbnRhY3QiOiLlj5HljIXmlrkiLCJkZXBhcnRtZW50IjpudWxsLCJwb3NpdGlvbiI6bnVsbCwiY2FNb2JpbGUiOiIxMzYwMTkyODE0MSIsImNhQ29udGFjdG9yIjoianhrIiwiZ3VpZCI6IlZzTXB0R2lmbnJWa0VlcnRRZUtod2YiLCJpc1NwbGl0U3VwcGxpZXIiOm51bGwsImlzU2h3ZiI6IjAiLCJvcGVuaW5nQmFua0hvbGRlciI6IuadjuWbmyIsInVzZXJNb2RpZnlBcHBseUlkIjpudWxsfSwiYnVzaW5lc3NMaW1pdCI6bnVsbCwiYWZ0ZXJVc2VyVHlwZSI6bnVsbH0sImV4cCI6MTU1MTc2OTczOSwiaWF0IjoxNTUxNzY3OTM5LCJhY2NvdW50IjoiZmJhbzUifQ.hkRMw85CImAXQJ6Ifl6jx7ShDnz65n40VXC7ZRWTwu121EmoRsJAaOvPr6ycaIFIfAc_EPaLeEL_XLVJFzRlcw"));
//        }
        //eyJhbGciOiJIUzUxMiJ9.eyJqd3RfdXNlcl9hdXRoX2luZm8iOnsiaWQiOjM0MSwiZ3VpZCI6IlZzTXB0R2lmbnJWa0VlcnRRZUtod2YiLCJwYXJlbnRHdWlkIjpudWxsLCJsb2dpbk5hbWUiOiJmYmFvNSIsInVzZXJTaG9ydG5hbWUiOm51bGwsInBhc3N3b3JkIjoiMjljOWQ1ZjhhZjBkZTgyZjUzNDI0YzY4NWJmYWVlOWIiLCJyZWdpc3Rlck1vYmlsZSI6IjEzMjQzNTQ1NDU2IiwicmVnaXN0ZXJFbWFpbCI6ImRzZnNmQHFxLmNvbSIsInVzZXJUeXBlIjoiMiIsImFjY291bnRUeXBlIjoiMSIsImFjY291bnRMZXZlbCI6IjAiLCJpc1ZhbGlkIjoiMSIsImNyZWF0ZVBlcnNvbiI6ImZiYW81IiwiY3JlYXRlRGF0ZSI6MTU0NDk3NjAwMDAwMCwibW9kaWZ5UGVyc29uIjpudWxsLCJtb2RpZnlEYXRlIjoxNTUxNzE1MjAwMDAwLCJ1c2VySW5mb0lkIjoxMTA2LCJsYXN0TG9naW5UaW1lIjoxNTUxNzY3MzI4MDAwLCJpbnZhbGlkUmVhc29uIjpudWxsLCJpbnZhbGlkRGF0ZSI6bnVsbCwiaW52YWxpZFBlcnNvbiI6bnVsbCwidXNlckluZm9EdG8iOnsidXNlckluZm9JZCI6MTEwNiwidXNlck5hbWUiOiLlj5HljIXotKblj7c1IiwidXNlclNob3J0bmFtZSI6IuWPkeWMhTUiLCJ1c2VyTm8iOiIxMTAwNSIsImVudGVycHJpc2VOYXR1cmUiOjEsImJ1c2luZXNzUmVxdWlyZW1lbnQiOm51bGwsInVzZXJUeXBlIjoiMiIsInN1cHBsaWVyVHlwZSI6MSwidXNlclNvdXJjZSI6bnVsbCwiY291bnRyeU5vIjoiQ0giLCJjaXR5Tm8iOjExMDEwMCwicmVnaXN0ZXJlZENhcGl0YWwiOm51bGwsImN1cnJlbmN5Tm8iOm51bGwsInNvY2lhbENyZWRpdENvZGUiOiIxMTIyMzM0NDU1NjY3Nzg4OTkiLCJidXNpbmVzc0xpY2Vuc2VCZWdpbkRhdGUiOjYzMTEyMzIwMDAwMCwiYnVzaW5lc3NMaWNlbnNlRW5kRGF0ZSI6MjUzNDAyMTg1NjAwMDAwLCJvcGVuaW5nQmFua05hbWUiOm51bGwsIm9wZW5pbmdCYW5rQWNjb3VudCI6bnVsbCwidGF4cGF5ZXJJZGVudGlmeU51bWJlciI6bnVsbCwiaXNHZW5lcmFsVGF4cGF5ZXIiOjEsImFkZHJlc3MiOiLmmK_lkKbmt7FWMSIsInppcCI6bnVsbCwidGVsZXBob25lIjoiMTMyMzU0Njc1NzQiLCJmYXgiOm51bGwsIndlYnNpdGVVcmwiOm51bGwsImlzRnNnIjoiMCIsImlzU3ViQ29tcCI6IjAiLCJpc1dlYiI6IjEiLCJpc1ZhbGlkIjoiMSIsIm1lbW8iOm51bGwsImNyZWF0ZVBlcnNvbiI6ImZiYW81IiwiY3JlYXRlRGF0ZSI6MTU0NDk3NjAwMDAwMCwibW9kaWZ5UGVyc29uIjpudWxsLCJtb2RpZnlEYXRlIjpudWxsLCJmcm96ZW5SZWFzb24iOm51bGwsImZyb3plbkRhdGUiOm51bGwsImNhQ3VzdG9tZXJJZCI6IjgxQTAwNkI4NDE2NUExNkRGMzgzRDg3QjIzQTNBNzM2IiwiY2FHZW5lcmF0ZVRpbWUiOjE1NTExMTA0MDAwMDAsImNvbnRhY3QiOiLlj5HljIXmlrkiLCJkZXBhcnRtZW50IjpudWxsLCJwb3NpdGlvbiI6bnVsbCwiY2FNb2JpbGUiOiIxMzYwMTkyODE0MSIsImNhQ29udGFjdG9yIjoianhrIiwiZ3VpZCI6IlZzTXB0R2lmbnJWa0VlcnRRZUtod2YiLCJpc1NwbGl0U3VwcGxpZXIiOm51bGwsImlzU2h3ZiI6IjAiLCJvcGVuaW5nQmFua0hvbGRlciI6IuadjuWbmyIsInVzZXJNb2RpZnlBcHBseUlkIjpudWxsfSwiYnVzaW5lc3NMaW1pdCI6bnVsbCwiYWZ0ZXJVc2VyVHlwZSI6bnVsbH0sImV4cCI6MTU1MTc2OTczOSwiaWF0IjoxNTUxNzY3OTM5LCJhY2NvdW50IjoiZmJhbzUifQ.hkRMw85CImAXQJ6Ifl6jx7ShDnz65n40VXC7ZRWTwu121EmoRsJAaOvPr6ycaIFIfAc_EPaLeEL_XLVJFzRlcw
        //eyJhbGciOiJIUzUxMiJ9.eyJqd3RfdXNlcl9hdXRoX2luZm8iOnsiaWQiOjc2LCJndWlkIjoiV0pXWUtZWDN0enhIRnFjcDVXRFhjeSIsInBhcmVudEd1aWQiOm51bGwsImxvZ2luTmFtZSI6ImZiYW80IiwidXNlclNob3J0bmFtZSI6bnVsbCwicGFzc3dvcmQiOiIyOWM5ZDVmOGFmMGRlODJmNTM0MjRjNjg1YmZhZWU5YiIsInJlZ2lzdGVyTW9iaWxlIjoiMTM1NjQ0NDU3NDkiLCJyZWdpc3RlckVtYWlsIjoiamlhcWkuc2hlbmdAZnNnLmNvbS5jbjIiLCJ1c2VyVHlwZSI6IjIiLCJhY2NvdW50VHlwZSI6IjEiLCJhY2NvdW50TGV2ZWwiOiIwIiwiaXNWYWxpZCI6IjEiLCJjcmVhdGVQZXJzb24iOm51bGwsImNyZWF0ZURhdGUiOjE1Mzc5Nzc2MDAwMDAsIm1vZGlmeVBlcnNvbiI6bnVsbCwibW9kaWZ5RGF0ZSI6MTU1MTcxNTIwMDAwMCwidXNlckluZm9JZCI6NDQsImxhc3RMb2dpblRpbWUiOjE1NTE3Njk1OTYwMDAsImludmFsaWRSZWFzb24iOm51bGwsImludmFsaWREYXRlIjpudWxsLCJpbnZhbGlkUGVyc29uIjpudWxsfSwic3ViIjoiU2luZ2xlIFNpZ24tb24iLCJpc3MiOiJRSS1TU08iLCJleHAiOjE1NTE3NzEwMTMsImlhdCI6MTU1MTc2OTIxMywiYWNjb3VudCI6ImZiYW80In0.hyvtNbyTUYPAUU6Lz9Qa_jGdmGKgOHt2v-1KfHoH6x08hXTribK-GEnHirBOI8HdFx49aGGZlz7hcVMqJn3mEQ
//        System.out.println(DateUtil.toDateTimeDash(new Date(1551767939)));
//        System.out.println(DateUtil.toDateTimeDash(new Date(1551769739)));
    }
}
