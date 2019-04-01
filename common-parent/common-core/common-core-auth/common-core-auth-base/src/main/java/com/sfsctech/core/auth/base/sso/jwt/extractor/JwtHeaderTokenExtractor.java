package com.sfsctech.core.auth.base.sso.jwt.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;

public class JwtHeaderTokenExtractor implements TokenExtractor {

    @Override
    public String extract(String token) {
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationServiceException("Authorization token cannot be blank!");
        }

        if (token.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization token size.");
        }

        return token.substring(HEADER_PREFIX.length() + 14);
    }
}
