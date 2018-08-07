package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import com.sfsctech.core.auth.sso.constants.SSOConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Class UserTokenUtil
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class JwtCookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtCookieUtil.class);

    /**
     * 根据Cookie获取JwtToken对象
     *
     * @param helper CookieHelper
     * @return JwtToken
     */
    public static JwtToken getJwtTokenByCookie(CookieHelper helper) {
        String token = helper.getCookieValue(SSOConstants.COOKIE_TOKEN_NAME);
        String saltCacheKey = helper.getCookieValue(SSOConstants.COOKIE_SALT_CACHE_KEY_NAME);
        if (StringUtil.isNotBlank(token)) {
            try {
                JwtToken jt = new JwtToken();
                jt.setJwt(URLDecoder.decode(token, LabelConstants.UTF8));
                jt.setSalt_CacheKey(URLDecoder.decode(saltCacheKey, LabelConstants.UTF8));
                return jt;
            } catch (UnsupportedEncodingException e) {
                clearJwtToken(helper);
                logger.error(ThrowableUtil.getRootMessage(e));
            }
        }
        return null;
    }

    public static JwtToken getJwtTokenByHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtil.isNotBlank(authorization)) {
            try {
                String[] token = authorization.split(LabelConstants.POUND);
                JwtToken jt = new JwtToken();
                jt.setJwt(URLDecoder.decode(token[0], LabelConstants.UTF8));
                jt.setSalt_CacheKey(URLDecoder.decode(token[1], LabelConstants.UTF8));
                return jt;
            } catch (Exception e) {
                logger.error(ThrowableUtil.getRootMessage(e));
            }
        }
        return null;
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper CookieHelper
     * @param jt     JwtToken
     */
    public static void updateJwtToken(CookieHelper helper, JwtToken jt) {
        updateJwtToken(helper, SSOConstants.COOKIE_TOKEN_NAME, SSOConstants.COOKIE_SALT_CACHE_KEY_NAME, jt);
    }

    public static void updateJwtToken(HttpServletResponse response, JwtToken jt) {
        response.setHeader("Authorization", jt.getJwt() + LabelConstants.POUND + jt.getSalt_CacheKey());
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper         CookieHelper
     * @param token_key      COOKIE_TOKEN_NAME
     * @param salt_cache_key COOKIE_SALT_CACHE_KEY_NAME
     * @param jt             JwtToken
     */
    public static void updateJwtToken(CookieHelper helper, String token_key, String salt_cache_key, JwtToken jt) {
        try {
            helper.setCookie(token_key, URLEncoder.encode(jt.getJwt(), LabelConstants.UTF8));
            helper.setCookie(salt_cache_key, URLEncoder.encode(jt.getSalt_CacheKey(), LabelConstants.UTF8));
            logger.info("set cookies token value [domain:" + helper.getConfig().getDomain() + " Jwt:" + jt.getJwt() + " Salt_CacheKey:" + jt.getSalt_CacheKey() + "]");
        } catch (UnsupportedEncodingException e) {
            clearJwtToken(helper);
            logger.error(ThrowableUtil.getRootMessage(e));
        }
    }

    /**
     * 清除JwtToken Cookie缓存
     *
     * @param helper CookieHelper
     */
    public static void clearJwtToken(CookieHelper helper) {
        helper.clearCookie(SSOConstants.COOKIE_TOKEN_NAME);
        helper.clearCookie(SSOConstants.COOKIE_SALT_CACHE_KEY_NAME);
    }
}
