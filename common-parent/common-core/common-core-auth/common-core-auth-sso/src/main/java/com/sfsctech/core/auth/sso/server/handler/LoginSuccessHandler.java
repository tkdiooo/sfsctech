package com.sfsctech.core.auth.sso.server.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import com.sfsctech.core.auth.sso.common.constants.SSOConstants;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.server.jwt.JwtToken;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenFactory;
import com.sfsctech.core.auth.sso.server.jwt.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.util.SessionKeepUtil;
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
        JwtToken accessJwt = jwtTokenFactory.generalAccessJwt(user);
        logger.info("用户：" + user.getUsername() + "，生成AccessJwt{" + accessJwt + "}。");
        // 生成RefreshJwt
        JwtToken refreshJwt = jwtTokenFactory.generalRefreshJwt(user);
        logger.info("用户：" + user.getUsername() + "，生成RefreshJwt{" + refreshJwt + "}。");
        String salt = HexUtil.getEncryptKey();
        logger.info("用户：" + user.getUsername() + "，生成盐值{" + salt + "}。");
        String cache_key = TokenExtractor.HEADER_PREFIX + user.getUsername() + LabelConstants.DOUBLE_POUND + salt;
        logger.info("用户：" + user.getUsername() + "，生成缓存KEY{" + cache_key + "}。");
        factory.getCacheClient().putTimeOut(cache_key, accessJwt, jwtTokenFactory.getSettings().getExpiration());

        String token = EncrypterTool.encrypt(EncrypterTool.Security.Des3ECBHex, cache_key + LabelConstants.DOUBLE_POUND + System.currentTimeMillis());
        logger.info("用户：" + user.getUsername() + "，生成token{" + token + "}。");
        CookieHelper helper = CookieHelper.getInstance(request, response);
        if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
            SessionKeepUtil.updateCertificate(helper, token);
        } else {
            SessionKeepUtil.updateCertificate(response, token);
        }
        super.transfer(request, response, this.successUrl);
    }
}
