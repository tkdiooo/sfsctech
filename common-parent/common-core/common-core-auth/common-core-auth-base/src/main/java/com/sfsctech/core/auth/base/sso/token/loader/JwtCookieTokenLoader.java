package com.sfsctech.core.auth.base.sso.token.loader;

import com.sfsctech.core.auth.base.sso.util.SessionKeepUtil;
import com.sfsctech.core.web.tools.cookie.CookieHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class JwtCookieTokenLoader
 *
 * @author 张麒 2019-4-3.
 * @version Description:
 */
public class JwtCookieTokenLoader implements TokenLoader {

    @Override
    public void loader(HttpServletRequest request, HttpServletResponse response, String token) {
        CookieHelper helper = CookieHelper.getInstance(request, response);
        SessionKeepUtil.updateCertificate(helper, token);
    }
}
