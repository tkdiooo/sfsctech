package com.sfsctech.core.security.csrf;


import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.security.properties.SecurityProperties;
import com.sfsctech.core.security.properties.StartProperties;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.UUIDUtil;

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

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<RedisProxy<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    private static StartProperties properties = SpringContextUtil.getBean(StartProperties.class);

    public static CSRFToken generateCSRFToken(HttpServletRequest request, HttpServletResponse response) {
        CSRFToken token = new CSRFToken();
        // Session保持
        if (properties.getProperties().getCSRF().getKeepPattern().equals(SecurityProperties.KeepPattern.Session)) {
            request.getSession().setAttribute(CSRF_TOKEN, token);
        }
        // Cache保持
        else if (properties.getProperties().getCSRF().getKeepPattern().equals(SecurityProperties.KeepPattern.Cache)) {
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
        if (properties.getProperties().getCSRF().getKeepPattern().equals(SecurityProperties.KeepPattern.Session)) {
            if (null != request.getSession().getAttribute(CSRF_TOKEN)) {
                token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
                request.getSession().removeAttribute(CSRF_TOKEN);
            }
        }
        // Cache保持
        else if (properties.getProperties().getCSRF().getKeepPattern().equals(SecurityProperties.KeepPattern.Cache)) {
            CookieHelper helper = CookieHelper.getInstance(request, response);
            String key = helper.getCookieValue(CSRF_TOKEN);
            if (StringUtil.isNotBlank(key)) {
                key = EncrypterTool.decrypt(EncrypterTool.Security.Aes, key);
                token = factory.get(key);
                factory.getCacheClient().remove(key);
            }
        }
        if (null != token) {
            String tokenValue = request.getParameter(token.getParameterName());
            if (StringUtil.isNotBlank(tokenValue) && tokenValue.equals(token.getToken())) {
                bool = false;
            }
        }
        return bool;
    }
}
