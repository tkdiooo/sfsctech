package com.sfsctech.security.csrf;

import com.sfsctech.base.session.SessionHolder;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.common.cookie.CookieHelper;
import com.sfsctech.common.security.EncrypterTool;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.uuid.UUIDUtil;
import com.sfsctech.constants.CommonConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class CSRFTokenManager
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class CSRFTokenManager {

    public static final String CSRF_TOKEN = "_csrf";

    public static CSRFToken generateCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = new CSRFToken();
        // Session保持
        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
            request.getSession().setAttribute(CSRF_TOKEN, token);
        }
        // 分布式保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN, token);
        }
        // Cache保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
            CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
            String key = UUIDUtil.base64Uuid();
            factory.getCacheClient().add(key, token);
            CookieHelper helper = CookieHelper.getInstance(request, response);
            helper.setCookie(CSRF_TOKEN, EncrypterTool.encrypt(EncrypterTool.Security.Aes, key));
        }
        return token;
    }

    public static boolean isValidCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = null;
        boolean bool = true;
        // Session保持
        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
        }
        // 分布式保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN);
        }
        // Cache保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
            CookieHelper helper = CookieHelper.getInstance(request, response);
            String key = helper.getCookieValue(CSRF_TOKEN);
            if (StringUtil.isNotBlank(key)) {
                CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
                token = (CSRFToken) factory.getCacheClient().get(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
            }
        }
        if (null != token) {
            String tokenValue = request.getParameter(token.getParameterName());
            if (StringUtil.isNotBlank(tokenValue) && tokenValue.equals(token.getToken())) {
                bool = false;
            }
        }
        destroy(request, response);
        return bool;
    }

    public static void destroy(HttpServletRequest request, HttpServletResponse response) {
        // Session保持
        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
            request.getSession().removeAttribute(CSRF_TOKEN);
        }
        // 分布式保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
            SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN);
        }
        // Cache保持
        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
            CookieHelper helper = CookieHelper.getInstance(request, response);
            String key = helper.getCookieValue(CSRF_TOKEN);
            if (StringUtil.isNotBlank(key)) {
                CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
                factory.getCacheClient().remove(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
            }
        }
    }
}
