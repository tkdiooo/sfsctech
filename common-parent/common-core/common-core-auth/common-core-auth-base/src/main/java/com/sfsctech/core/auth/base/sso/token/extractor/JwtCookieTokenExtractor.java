package com.sfsctech.core.auth.base.sso.token.extractor;

import com.sfsctech.core.auth.base.sso.constants.SSOConstants;
import com.sfsctech.core.auth.base.sso.util.SessionKeepUtil;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class JwtCookieTokenExtractor
 *
 * @author 张麒 2019-4-3.
 * @version Description:
 */
public class JwtCookieTokenExtractor implements TokenExtractor {

    public String extract(String payload) {
        if (StringUtil.isBlank(payload)) {
            throw new AuthenticationServiceException("Authorization cookie cannot be blank!");
        }

        if (payload.length() < SSOConstants.TOKEN_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization cookie size.");
        }

        return payload.substring(SSOConstants.TOKEN_PREFIX.length());
    }

    @Override
    public String extract(HttpServletRequest request, HttpServletResponse response) {
        CookieHelper helper = CookieHelper.getInstance(request, response);
        return decrypt(extract(SessionKeepUtil.getCertificateByCookie(helper)));
    }
}
