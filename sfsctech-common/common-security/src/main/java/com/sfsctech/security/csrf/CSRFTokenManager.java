package com.sfsctech.security.csrf;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.security.session.SessionHolder;

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
        if (!SecurityConstants.SERVER_SOA) {
            request.getSession().setAttribute(CSRF_TOKEN, token);
        } else {
            SessionHolder.getSessionInfo().setAttribute(CSRF_TOKEN, token);
        }
        return token;
    }

    public static boolean isValidCSRFToken(HttpServletRequest request) {
        CSRFToken token;
        boolean bool = true;
        if (!SecurityConstants.SERVER_SOA) {
            token = (CSRFToken) request.getSession().getAttribute(CSRF_TOKEN);
        } else {
            token = (CSRFToken) SessionHolder.getSessionInfo().getAttribute(CSRF_TOKEN);
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
        if (SecurityConstants.SERVER_SOA) {
            if (null != SessionHolder.getSessionInfo()) {
                SessionHolder.getSessionInfo().removeAttribute(CSRF_TOKEN);
            }
        } else {
            request.getSession().removeAttribute(CSRF_TOKEN);
        }
    }
}
