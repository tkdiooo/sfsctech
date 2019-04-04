package com.sfsctech.core.auth.sso.server.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import com.sfsctech.core.auth.base.sso.constants.SSOConstants;
import com.sfsctech.core.auth.base.sso.jwt.AccessJwtToken;
import com.sfsctech.core.auth.base.sso.token.loader.TokenLoader;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenFactory;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenStore;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.support.common.security.EncrypterTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class LoginSuccessHandler
 *
 * @author 张麒 2019-1-24.
 * @version Description:
 */
public class LoginSuccessHandler extends BaseSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private String successUrl;

    private TokenLoader tokenLoader;

    private JwtTokenFactory jwtTokenFactory;

    private CacheFactory<RedisProxy<String, Object>> factory;

    private ApplicationInitialize applicationInitialize;

    public LoginSuccessHandler(CacheFactory<RedisProxy<String, Object>> factory, JwtTokenFactory jwtTokenFactory, TokenLoader tokenLoader, String successUrl, ApplicationInitialize applicationInitialize) {
        this.factory = factory;
        this.jwtTokenFactory = jwtTokenFactory;
        this.tokenLoader = tokenLoader;
        this.successUrl = successUrl;
        this.applicationInitialize = applicationInitialize;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        init(request, authentication);
        User user = ((User) authentication.getPrincipal());
        // 生成AccessJwt
        AccessJwtToken accessJwt = jwtTokenFactory.generalAccessJwt(user);
        logger.info("用户:{}，生成AccessJwt:{}", user.getUsername(), accessJwt.getToken());
        String Access_Jwt_Cache = factory.generateCacheKey(applicationInitialize.getAppName(), SSOConstants.CACHE_IDENTIFY_ACCESS_TOKEN, user.getUsername());
        logger.info("用户:{}，生成Access_Jwt_Cache:{}", user.getUsername(), Access_Jwt_Cache);
        factory.getCacheClient().putTimeOut(Access_Jwt_Cache, accessJwt.getToken(), (int) jwtTokenFactory.getSettings().getExpiration().getSeconds());
        String Access_Jwt_Token = EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, Access_Jwt_Cache);
        logger.info("用户:{}，生成Access_Jwt_Token:{}", user.getUsername(), Access_Jwt_Token);
        // 设置token保存至表头
        SessionKeepUtil.updateCertificate(response, Access_Jwt_Token);
        super.transfer(request, response, this.successUrl);

        clearAuthenticationAttributes(request);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
