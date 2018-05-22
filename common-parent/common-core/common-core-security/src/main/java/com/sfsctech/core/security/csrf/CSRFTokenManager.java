package com.sfsctech.core.security.csrf;


import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class CSRFTokenManager
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
// TODO 重构
public class CSRFTokenManager {

    public static final String CSRF_TOKEN = "_csrf";

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<RedisProxy<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    public static CSRFToken generateCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = new CSRFToken();
        // Session保持
//        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
//            request.getSession().setAttribute(CSRF_TOKEN, token);
//        }
        // 分布式保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
//            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN, token);
//        }
        // Cache保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
//            String key = UUIDUtil.base64Uuid();
//            factory.getCacheClient().add(key, token);
//            CookieHelper helper = CookieHelper.getInstance(request, response);
//            helper.setCookie(CSRF_TOKEN, EncrypterTool.encrypt(EncrypterTool.Security.Aes, key));
//        }
        return token;
    }

    public static boolean isValidCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = null;
        boolean bool = true;
        // Session保持
//        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
//            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
//        }
        // 分布式保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
//            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN);
//        }
        // Cache保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
//            CookieHelper helper = CookieHelper.getInstance(request, response);
//            String key = helper.getCookieValue(CSRF_TOKEN);
//            if (StringUtil.isNotBlank(key)) {
//                token = factory.get(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
//            }
//        }
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
//        if (StringUtil.isBlank(CommonConstants.CSRF_KEEP_PATTERN)) {
//            request.getSession().removeAttribute(CSRF_TOKEN);
//        }
        // 分布式保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_SOA)) {
//            SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN);
//        }
        // Cache保持
//        else if (CommonConstants.CSRF_KEEP_PATTERN.equals(CommonConstants.CSRF_KEEP_PATTERN_CACHE)) {
//            CookieHelper helper = CookieHelper.getInstance(request, response);
//            String key = helper.getCookieValue(CSRF_TOKEN);
//            if (StringUtil.isNotBlank(key)) {
//                factory.getCacheClient().remove(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
//            }
//        }
    }
}
