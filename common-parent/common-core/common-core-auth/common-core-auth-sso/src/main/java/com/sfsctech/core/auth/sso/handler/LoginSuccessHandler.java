package com.sfsctech.core.auth.sso.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import com.sfsctech.core.auth.sso.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.util.JwtCookieUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.security.EncrypterTool;
import com.sfsctech.support.common.util.HexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private SSOProperties properties;

    private CacheFactory<RedisProxy<String, Object>> factory;

    public LoginSuccessHandler(CacheFactory<RedisProxy<String, Object>> factory, SSOProperties properties, String successUrl) {
        this.factory = factory;
        this.properties = properties;
        this.successUrl = successUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        init(request, authentication);
        User user = ((User) authentication.getPrincipal());
        // 生成Jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(SSOConstants.JWT_USER_AUTH_INFO, user);
        String jwt = JwtUtil.generalJwt(claims);
        logger.info("用户：" + user.getUsername() + "，生成jwt{" + jwt + "}。");
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + user.getUsername() + "，生成盐值{" + salt + "}。");
        String cache_key = SSOConstants.SSO_CACHE_IDENTIFY + LabelConstants.PERIOD + user.getUsername() + LabelConstants.DOUBLE_POUND + salt;
        logger.info("用户：" + user.getUsername() + "，生成缓存KEY{" + cache_key + "}。");
        factory.getCacheClient().putTimeOut(cache_key, jwt, JwtUtil.getConfig().getExpiration());
        String token = EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, cache_key + LabelConstants.DOUBLE_POUND + System.currentTimeMillis());
        logger.info("用户：" + user.getUsername() + "，生成token{" + token + "}。");
        CookieHelper helper = CookieHelper.getInstance(request, response);
        if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
            JwtCookieUtil.updateJwtToken(helper, token);
        } else {
            JwtCookieUtil.updateJwtToken(response, token);
        }
        super.transfer(request, response, this.successUrl);
    }
}
