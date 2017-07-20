package com.sfsctech.security.csrf;

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

    }

    public static boolean validCsrfToken(HttpServletRequest request) {
        return false;
    }

    public static void destroy() {

    }
}
