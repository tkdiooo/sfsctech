package com.sfsctech.core.auth.sso.base.token.extractor;

import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public interface TokenExtractor {

    String extract(HttpServletRequest request, HttpServletResponse response);

    default String decrypt(String payload) {
        String token = EncrypterTool.decrypt(EncrypterTool.Security.AesCBC, payload);
        if (StringUtil.isBlank(token)) {
            throw new AuthenticationServiceException("解密失败,token为空!");
        }
        return token;
    }
}
