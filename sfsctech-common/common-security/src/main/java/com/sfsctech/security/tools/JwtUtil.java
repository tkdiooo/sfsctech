package com.sfsctech.security.tools;

/**
 * Class JwtUtil
 *
 * @author 张麒 2017/8/27.
 * @version Description:
 */
public class JwtUtil {

    public static String generalJwt() {
//        try {
//
//            Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);//compactJws为jwt字符串
//            Claims body = parseClaimsJws.getBody();//得到body后我们可以从body中获取我们需要的信息
//            //比如 获取主题,当然，这是我们在生成jwt字符串的时候就已经存进来的
//            String subject = body.getSubject();
//
//
//            //OK, we can trust this JWT
//
//        } catch (SignatureException | MalformedJwtException e) {
//            // TODO: handle exception
//            // don't trust the JWT!
//            // jwt 解析错误
//        } catch (ExpiredJwtException e) {
//            // TODO: handle exception
//            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
//        }
        return "";
    }

//    private String jianshu; /** * 由字符串生成加密key * @return */ public SecretKey generalKey(){ String stringKey = jianshu+Constant.JWT_SECRET; byte[] encodedKey = Base64.decodeBase64(stringKey); SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES"); return key; } /** * 创建jwt * @param id * @param subject * @param ttlMillis * @return * @throws Exception */ public String createJWT(String id, String subject, long ttlMillis) throws Exception { SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; long nowMillis = System.currentTimeMillis(); Date now = new Date(nowMillis); SecretKey key = generalKey(); JwtBuilder builder = Jwts.builder() .setId(id) .setIssuedAt(now) .setSubject(subject) .signWith(signatureAlgorithm, key); if (ttlMillis >= 0) { long expMillis = nowMillis + ttlMillis; Date exp = new Date(expMillis); builder.setExpiration(exp); } return builder.compact(); } /** * 解密jwt * @param jwt * @return * @throws Exception */ public Claims parseJWT(String jwt) throws Exception{ SecretKey key = generalKey(); Claims claims = Jwts.parser() .setSigningKey(key) .parseClaimsJws(jwt).getBody(); return claims; } /** * 生成subject信息 * @param user * @return */ public static String generalSubject(t_user user){ JSONObject jo = new JSONObject(); jo.put("userId", user.getId()); jo.put("mobile", user.getMobile()); return jo.toJSONString(); } }
//
//作者：NAVER_say_NAVER
//        链接：http://www.jianshu.com/p/d215e70dc1f9
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
