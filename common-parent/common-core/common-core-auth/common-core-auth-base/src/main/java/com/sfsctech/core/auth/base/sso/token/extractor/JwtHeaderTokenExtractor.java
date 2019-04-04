package com.sfsctech.core.auth.base.sso.token.extractor;

import com.sfsctech.core.auth.base.sso.constants.SSOConstants;
import com.sfsctech.core.auth.base.sso.util.SessionKeepUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtHeaderTokenExtractor implements TokenExtractor {

    private String extract(String header) {
        if (StringUtil.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < SSOConstants.TOKEN_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(SSOConstants.TOKEN_PREFIX.length());
    }

    @Override
    public String extract(HttpServletRequest request, HttpServletResponse response) {
        return decrypt(extract(SessionKeepUtil.getCertificateByHeader(request)));
    }
}
