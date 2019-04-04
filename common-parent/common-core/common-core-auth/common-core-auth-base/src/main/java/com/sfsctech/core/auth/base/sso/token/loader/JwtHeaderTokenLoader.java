package com.sfsctech.core.auth.base.sso.token.loader;

import com.sfsctech.core.auth.base.sso.util.SessionKeepUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class JwtHeaderTokenLoader
 *
 * @author 张麒 2019-4-3.
 * @version Description:
 */
public class JwtHeaderTokenLoader implements TokenLoader {

    @Override
    public void loader(HttpServletRequest request, HttpServletResponse response, String token) {
        SessionKeepUtil.updateCertificate(response, token);
    }

}
