package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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
    public static String getJwtTokenByCookie(CookieHelper helper) {
        logger.info("通过Cookie获取Authorization信息");
        String token = helper.getCookieValue(SSOConstants.COOKIE_TOKEN_NAME);
        logger.info("token:" + token);
        if (StringUtil.isBlank(token)) {
            logger.info("用户Authorization信息获取为空");
        }
        return token;
    }

    public static String getJwtTokenByHeader(HttpServletRequest request) {
        logger.info("通过request header获取Authorization信息");
        String authorization = request.getHeader("Authorization");
        logger.info("Authorization:" + authorization);
        if (StringUtil.isBlank(authorization)) {
            logger.info("用户Authorization信息获取为空");
        }
        return authorization;
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper CookieHelper
     * @param token  Token
     */
    public static void updateJwtToken(CookieHelper helper, String token) {
        updateJwtToken(helper, SSOConstants.COOKIE_TOKEN_NAME, token);
    }

    /**
     * 根据UserToken更新Header
     *
     * @param response HttpServletResponse
     * @param token    Token
     */
    public static void updateJwtToken(HttpServletResponse response, String token) {
        response.setHeader("Authorization", token);
    }

    /**
     * 根据UserToken更新Cookie
     *
     * @param helper    CookieHelper
     * @param token_key COOKIE_TOKEN_NAME
     * @param token     COOKIE_TOKEN_VALUE
     */
    public static void updateJwtToken(CookieHelper helper, String token_key, String token) {
        try {
            helper.setCookie(token_key, URLEncoder.encode(token, LabelConstants.UTF8));
            logger.info("set cookies token value [domain:{} token:{}]", helper.getConfig().getDomain(), token);
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
    }
}
