package com.sfsctech.core.auth.sso.base.util;

import com.sfsctech.core.auth.sso.base.constants.SSOConstants;
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
 * Class SessionKeepUtil
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class SessionKeepUtil {

    private static final Logger logger = LoggerFactory.getLogger(SessionKeepUtil.class);

    /**
     * 根据Cookie获取Certificate对象
     *
     * @param helper CookieHelper
     * @return Certificate
     */
    public static String getCertificateByCookie(CookieHelper helper) {
        logger.info("通过Cookie获取Authorization信息");
        String certificate = helper.getCookieValue(SSOConstants.TOKEN_IDENTIFY_COOKIE);
        logger.info("session certificate:{}", certificate);
        if (StringUtil.isBlank(certificate)) {
            logger.info("用户certificate信息获取为空");
        }
        return certificate;
    }

    public static String getCertificateByHeader(HttpServletRequest request) {
        logger.info("通过request header获取Authorization信息");
        String certificate = request.getHeader(SSOConstants.TOKEN_IDENTIFY_HEADER);
        logger.info("session certificate:{}", certificate);
        if (StringUtil.isBlank(certificate)) {
            logger.info("用户certificate信息获取为空");
        }
        return certificate;
    }

    /**
     * 根据Certificate更新Cookie
     *
     * @param helper      CookieHelper
     * @param certificate 凭证
     */
    public static void updateCertificate(CookieHelper helper, String certificate) {
        updateCertificate(helper, SSOConstants.TOKEN_IDENTIFY_COOKIE, certificate);
    }

    /**
     * 根据Certificate更新Header
     *
     * @param response    HttpServletResponse
     * @param certificate 凭证
     */
    public static void updateCertificate(HttpServletResponse response, String certificate) {
        response.setHeader(SSOConstants.TOKEN_IDENTIFY_HEADER, certificate);
    }

    /**
     * 根据Certificate更新Cookie
     *
     * @param helper      CookieHelper
     * @param key         COOKIE_NAME
     * @param certificate COOKIE_VALUE
     */
    public static void updateCertificate(CookieHelper helper, String key, String certificate) {
        try {
            helper.setCookie(key, URLEncoder.encode(certificate, LabelConstants.UTF8));
            logger.info("set cookie value value [domain:{} certificate:{}]", helper.getConfig().getDomain(), certificate);
        } catch (UnsupportedEncodingException e) {
            clearCertificate(helper);
            logger.error(ThrowableUtil.getRootMessage(e));
        }
    }

    /**
     * 清除Certificate Cookie缓存
     *
     * @param helper CookieHelper
     */
    public static void clearCertificate(CookieHelper helper) {
        helper.clearCookie(SSOConstants.TOKEN_IDENTIFY_COOKIE);
    }
}
