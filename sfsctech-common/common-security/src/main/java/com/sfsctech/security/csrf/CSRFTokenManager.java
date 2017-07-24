package com.sfsctech.security.csrf;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.security.session.SessionHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Class CSRFTokenManager
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class CSRFTokenManager {

    private static String CSRF_TOKEN_SESSION = "_csrf";

    public static void generateCSRFToken(HttpServletRequest request) {
        // 销毁token
        destroy(request);
        CSRFToken token = new CSRFToken();
        if (null != SessionHolder.getSessionInfo()) {
            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN_SESSION, token);
        } else {
            request.getSession().setAttribute(CSRF_TOKEN_SESSION, token);
        }
    }

    public static boolean isValidCSRFToken(HttpServletRequest request) {
        CSRFToken token;
        if (null != SessionHolder.getSessionInfo()) {
            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN_SESSION);
        } else {
            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN_SESSION);
        }
        String tokenValue = request.getParameter(token.getParameterName());
        if (StringUtil.isNotBlank(tokenValue) && tokenValue.equals(token.getToken())) {
            return false;
        }
        return true;
    }

    public static void destroy(HttpServletRequest request) {
        if (null != SessionHolder.getSessionInfo()) {
            SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN_SESSION);
        } else {
            request.getSession().removeAttribute(CSRF_TOKEN_SESSION);
        }
    }
}
