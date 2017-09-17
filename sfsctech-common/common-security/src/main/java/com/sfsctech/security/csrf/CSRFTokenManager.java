package com.sfsctech.security.csrf;

import com.sfsctech.auth.session.SessionHolder;
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
        // 缓存处理
        if (StringUtil.isBlank(CommonConstants.SESSION_AUTHENTICATION)) {
            CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
            String key = UUIDUtil.base64Uuid();
            factory.getCacheClient().add(key, token);
            CookieHelper helper = CookieHelper.getInstance(request, response);
            helper.setCookie(CSRF_TOKEN, EncrypterTool.encrypt(EncrypterTool.Security.Aes, key));
        }
        // 分布式服务
        else if (CommonConstants.SESSION_AUTHENTICATION.equals(CommonConstants.SERVICE_SOA)) {
            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN, token);
        }
        // Session处理
        else {
            request.getSession().setAttribute(CSRF_TOKEN, token);
        }
        return token;
    }

    public static boolean isValidCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = null;
        boolean bool = true;
        // 缓存处理
        if (StringUtil.isBlank(CommonConstants.SESSION_AUTHENTICATION)) {
            CookieHelper helper = CookieHelper.getInstance(request, response);
            String key = helper.getCookieValue(CSRF_TOKEN);
            if (StringUtil.isNotBlank(key)) {
                CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
                token = (CSRFToken) factory.getCacheClient().get(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
            }
        }
        // 分布式服务
        else if (CommonConstants.SESSION_AUTHENTICATION.equals(CommonConstants.SERVICE_SOA)) {
            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN);
        }
        // Session处理
        else {
            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
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
        // 缓存处理
        if (StringUtil.isBlank(CommonConstants.SESSION_AUTHENTICATION)) {
            CookieHelper helper = CookieHelper.getInstance(request, response);
            String key = helper.getCookieValue(CSRF_TOKEN);
            if (StringUtil.isNotBlank(key)) {
                CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);
                factory.getCacheClient().remove(EncrypterTool.decrypt(EncrypterTool.Security.Aes, key));
            }
        } else if (CommonConstants.SESSION_AUTHENTICATION.equals(CommonConstants.SERVICE_SOA)) {
            SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN);
        } else {
            request.getSession().removeAttribute(CSRF_TOKEN);
        }
    }
}
