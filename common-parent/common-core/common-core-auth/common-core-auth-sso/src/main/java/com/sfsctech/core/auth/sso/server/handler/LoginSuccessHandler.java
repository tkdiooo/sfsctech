package com.sfsctech.core.auth.sso.server.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import com.sfsctech.core.auth.sso.common.constants.SSOConstants;
import com.sfsctech.core.auth.sso.common.properties.SSOProperties;
import com.sfsctech.core.auth.sso.server.jwt.AccessJwtToken;
import com.sfsctech.core.auth.sso.common.jwt.JwtTokenFactory;
import com.sfsctech.core.auth.sso.common.jwt.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.common.util.SessionKeepUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
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

/**
 * Class LoginSuccessHandler
 *
 * @author 张麒 2019-1-24.
 * @version Description:
 */
public class LoginSuccessHandler extends BaseSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private String successUrl;

    private SSOProperties properties;

    private JwtTokenFactory jwtTokenFactory;

    private CacheFactory<RedisProxy<String, Object>> factory;

    public LoginSuccessHandler(CacheFactory<RedisProxy<String, Object>> factory, JwtTokenFactory jwtTokenFactory, SSOProperties properties, String successUrl) {
        this.factory = factory;
        this.jwtTokenFactory = jwtTokenFactory;
        this.properties = properties;
        this.successUrl = successUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        init(request, authentication);
        User user = ((User) authentication.getPrincipal());
        // 生成AccessJwt
        AccessJwtToken accessJwt = jwtTokenFactory.generalAccessJwt(user);
        logger.info("用户:{}，生成AccessJwt:{}", user.getUsername(), accessJwt.getJwt());
        String Access_Jwt_Cache = SSOConstants.ACCESS_TOKEN_CACHE_IDENTIFY + LabelConstants.DOUBLE_POUND + user.getUsername();
        logger.info("用户:{}，生成Access_Jwt_Cache:{}", user.getUsername(), Access_Jwt_Cache);
        factory.getCacheClient().putTimeOut(Access_Jwt_Cache, accessJwt.getJwt(), jwtTokenFactory.getSettings().getExpiration());
        String Access_Jwt_Token = EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, TokenExtractor.HEADER_PREFIX + System.currentTimeMillis() + LabelConstants.PERIOD + Access_Jwt_Cache);
        logger.info("用户:{}，生成Access_Jwt_Token:{}", user.getUsername(), Access_Jwt_Token);
        CookieHelper helper = CookieHelper.getInstance(request, response);
        if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
            SessionKeepUtil.updateCertificate(helper, Access_Jwt_Token);
        } else {
            SessionKeepUtil.updateCertificate(response, Access_Jwt_Token);
        }
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
