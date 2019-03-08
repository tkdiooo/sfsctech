package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.HexUtil;
import com.sfsctech.support.common.util.ListUtil;
import com.sfsctech.support.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;

/**
 * Class SSOUtil
 *
 * @author 张麒 2018-7-19.
 * @version Description:
 */
public class SSOUtil {

    private static JwtUtil jwtUtil = SpringContextUtil.getBean(JwtUtil.class);

    public static RpcResult<JwtToken> generalVerify(JwtToken jt, Logger logger) {
        RpcResult<JwtToken> result = new RpcResult<>();
        // 校验salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3ECBHex, jt.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            result.setMessage("用户校验失败:[" + salt_CacheKey + "] 无法解密出salt_CacheKey。");
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        logger.info("用户校验:salt_CacheKey[" + salt_CacheKey + "]。");
        // 校验salt
        String salt = String.valueOf(SingletonUtil.getCacheFactory().getCacheClient().get(salt_CacheKey));
        if (StringUtil.isBlank(salt)) {
            result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的解密salt！");
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        logger.info("用户校验:salt[" + salt + "]。");

        // 校验Jwt_CacheKey
        String jwt_CacheKey = EncrypterTool.decrypt(jt.getJwt(), salt);
        if (StringUtil.isBlank(jwt_CacheKey)) {
            result.setMessage("用户校验失败:[" + jwt_CacheKey + "] 无法解密出jwt_CacheKey。");
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        result.addAttach("jwt_CacheKey", jwt_CacheKey);

        // 校验Jwt
        String jwt = String.valueOf(SingletonUtil.getCacheFactory().getCacheClient().get(jwt_CacheKey));
        if (StringUtil.isBlank(jwt)) {
            result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的jwt信息！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        result.addAttach("jwt", jwt);
        result.setResult(jt);
        return result;
    }

    public static void refreshJwt(Claims claims, String account, JwtToken jwtToken, Logger logger) {
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3ECBHex, jwtToken.getSalt_CacheKey());
        String salt = SingletonUtil.getCacheFactory().get(salt_CacheKey);
        String jwt_CacheKey = EncrypterTool.decrypt(jwtToken.getJwt(), salt);
        // 清除salt_CacheKey
        SingletonUtil.getCacheFactory().getCacheClient().remove(salt_CacheKey);
        // 清除jwt_CacheKey
        SingletonUtil.getCacheFactory().getCacheClient().remove(jwt_CacheKey);
        logger.info("刷新登录用户:" + account + "的Jwt信息");
        String jwt = jwtUtil.generalJwt(claims);
        logger.info("用户:" + account + "，生成新的jwt[" + jwt + "]。");
        // 生成Jwt缓存key
        jwt_CacheKey = CacheKeyUtil.getSaltCacheKey();
        logger.info("用户：" + account + "，生成的jwt_CacheKey[" + jwt_CacheKey + "]。");
        // 缓存Jwt
        SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(jwt_CacheKey, jwt, jwtUtil.getConfig().getExpirationBySecond());
        //生成加密盐值，用于加密jwt_CacheKey信息
        salt = HexUtil.getEncryptKey();
        logger.info("用户:" + account + "，生成新的加密盐值[" + salt + "]。");
        // 加密jwt_CacheKey
        String token = EncrypterTool.encrypt(jwt_CacheKey, salt);
        logger.info("用户：" + account + "，生成的token[" + token + "]。");
        // 生成新的salt_CacheKey
        salt_CacheKey = CacheKeyUtil.getSaltCacheKey();
        logger.info("用户:" + account + "，生成新的salt_CacheKey[" + salt_CacheKey + "]。");
        // 缓存salt
        SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(salt_CacheKey, salt, jwtUtil.getConfig().getExpirationBySecond());
        jwtToken.setSalt(salt);
        jwtToken.setJwt(token);
        jwtToken.setSalt_CacheKey(EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, salt_CacheKey));
    }

//    private static void saltCacheKey(RpcResult<JwtToken> result, String salt_CacheKey) {
//        result.setMessage("用户校验失败:[" + salt_CacheKey + "] 无法解密出salt_CacheKey。");
//        result.setSuccess(false);
//        result.setStatus(RpcConstants.Status.Failure);
//    }

//    private static void salt(RpcResult<JwtToken> result) {
//        result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的解密salt！");
//        result.setSuccess(false);
//        result.setStatus(RpcConstants.Status.Failure);
//    }
}
