package com.sfsctech.security.csrf;

import com.sfsctech.auth.session.SessionHolder;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.ExcludesConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Class CSRFTokenManager
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class CSRFTokenManager {

    public static final String CSRF_TOKEN = "_csrf";

    public static CSRFToken generateCSRFToken(HttpServletRequest request) {
        CSRFToken token = new CSRFToken();
        if (StringUtil.isNotBlank(ExcludesConstants.SESSION_AUTHENTICATION) && ExcludesConstants.SESSION_AUTHENTICATION.equals(ExcludesConstants.SERVICE_SOA)) {
            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN, token);
        } else {
            request.getSession().setAttribute(CSRF_TOKEN, token);
        }
        return token;
    }

    public static boolean isValidCSRFToken(HttpServletRequest request) {
        CSRFToken token;
        boolean bool = true;
        if (StringUtil.isNotBlank(ExcludesConstants.SESSION_AUTHENTICATION) && ExcludesConstants.SESSION_AUTHENTICATION.equals(ExcludesConstants.SERVICE_SOA)) {
            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN);
        } else {
            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
        }
        if (null != token) {
            String tokenValue = request.getParameter(token.getParameterName());
            if (StringUtil.isNotBlank(tokenValue) && tokenValue.equals(token.getToken())) {
                bool = false;
            }
        }
        destroy(request);
        return bool;
    }

    public static void destroy(HttpServletRequest request) {
        if (StringUtil.isNotBlank(ExcludesConstants.SESSION_AUTHENTICATION) && ExcludesConstants.SESSION_AUTHENTICATION.equals(ExcludesConstants.SERVICE_SOA)) {
            SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN);
        } else {
            request.getSession().removeAttribute(CSRF_TOKEN);
        }
    }
}
