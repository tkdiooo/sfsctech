package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.auth.sso.properties.JwtProperties;
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

    private static JwtProperties config = SpringContextUtil.getBean(JwtProperties.class);

    public static RpcResult<JwtToken> generalVerify(JwtToken jt, Logger logger) {
        RpcResult<JwtToken> result = new RpcResult<>();
        // 解密salt_CacheKey
        String salt_CacheKey = EncrypterTool.decrypt(EncrypterTool.Security.Des3, jt.getSalt_CacheKey());
        if (StringUtil.isBlank(salt_CacheKey)) {
            saltCacheKey(result, jt.getSalt_CacheKey());
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        logger.info("用户校验：salt_CacheKey[" + salt_CacheKey + "]。");

        String salt = String.valueOf(SingletonUtil.getCacheFactory().getCacheClient().get(salt_CacheKey));
        if (StringUtil.isBlank(salt)) {
            salt(result);
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            return result;
        }
        jt.setSalt(salt);
        logger.info("用户校验：salt[" + salt + "]。");

        // 从缓存中获取token信息
        String token = String.valueOf(SingletonUtil.getCacheFactory().getCacheClient().get(salt_CacheKey + LabelConstants.POUND + salt));
        if (StringUtil.isBlank(token)) {
            result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的token信息！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }

        if (!token.equals(jt.getJwt())) {
            result.setMessage("用户校验失败 :用户token信息不匹配！");
            logger.error(ListUtil.toString(result.getMessages(), LabelConstants.COMMA));
            result.setSuccess(false);
            result.setStatus(RpcConstants.Status.Failure);
            return result;
        }
        result.addAttach("salt_CacheKey", salt_CacheKey);
        result.setResult(jt);
        return result;
    }

    public static void refreshJwt(Claims claims, String account, String salt_CacheKey, JwtToken jwtToken, Logger logger) {
        logger.info("刷新登录用户：" + account + "的Jwt信息");
        String jwt = JwtUtil.generalJwt(claims);
        logger.info("用户：" + account + "，生成新的jwt[" + jwt + "]。");
        // 生成新的salt
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + account + "，生成新的加密盐值[" + salt + "]。");
        // 加密JwtToken
        String token = EncrypterTool.encrypt(jwt, salt);
        logger.info("用户：" + account + "，生成新的token[" + token + "]。");
        // 清除salt_CacheKey
        SingletonUtil.getCacheFactory().getCacheClient().remove(salt_CacheKey);
        // 生成新的salt_CacheKey
        salt_CacheKey = CacheKeyUtil.getSaltCacheKey();
        logger.info("用户：" + account + "，生成新的salt_CacheKey[" + salt_CacheKey + "]。");
        // 缓存salt
        SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(salt_CacheKey, salt, config.getExpiration().intValue());
        // 缓存token
        SingletonUtil.getCacheFactory().getCacheClient().putTimeOut(salt_CacheKey + LabelConstants.POUND + salt, token, config.getExpiration().intValue());
        jwtToken.setJwt(token);
        jwtToken.setSalt(salt);
        jwtToken.setSalt_CacheKey(EncrypterTool.encrypt(EncrypterTool.Security.Des3, salt_CacheKey));
    }

    private static void saltCacheKey(RpcResult<JwtToken> result, String salt_CacheKey) {
        result.setMessage("用户校验失败：[" + salt_CacheKey + "] 无法解密出salt_CacheKey。");
        result.setSuccess(false);
        result.setStatus(RpcConstants.Status.Failure);
    }

    private static void salt(RpcResult<JwtToken> result) {
        result.setMessage("用户校验失败 :用户登录超时，已无法找到缓存中的解密salt！");
        result.setSuccess(false);
        result.setStatus(RpcConstants.Status.Failure);
    }
}
