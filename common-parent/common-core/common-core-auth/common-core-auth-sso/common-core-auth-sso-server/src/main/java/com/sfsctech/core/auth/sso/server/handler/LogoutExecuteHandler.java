package com.sfsctech.core.auth.sso.server.handler;

import com.sfsctech.core.auth.sso.base.constants.SSOConstants;
import com.sfsctech.core.auth.sso.base.token.extractor.TokenExtractor;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenFactory;
import com.sfsctech.core.auth.sso.server.jwt.JwtTokenStore;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.support.common.util.DateUtil;
import com.sfsctech.support.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Class LogoutHandler
 *
 * @author 张麒 2019-4-17.
 * @version Description:
 */
public class LogoutExecuteHandler implements LogoutHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutExecuteHandler.class);
    private final TokenExtractor tokenExtractor;
    private JwtTokenFactory jwtTokenFactory;
    private ApplicationInitialize applicationInitialize;
    private CacheFactory<RedisProxy<String, Object>> factory;

    public LogoutExecuteHandler(TokenExtractor tokenExtractor, JwtTokenFactory jwtTokenFactory, ApplicationInitialize applicationInitialize, CacheFactory<RedisProxy<String, Object>> factory) {
        this.tokenExtractor = tokenExtractor;
        this.jwtTokenFactory = jwtTokenFactory;
        this.applicationInitialize = applicationInitialize;
        this.factory = factory;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("token提取类:{}", tokenExtractor.getClass());
        String Access_Jwt_Cache = tokenExtractor.extract(request, response);
        String jwt = factory.get(Access_Jwt_Cache);
        if (StringUtil.isBlank(jwt)) {
            logger.info("用户登出失败,Jwt信息为空");
        } else {
            logger.info("获取JwtToken:{}", jwt);
            Jws<Claims> jwsClaims = jwtTokenFactory.parseJWT(jwt);
            Date date = DateUtil.getCurrentDate();
            // 设置用户登出时间
            String tokenStoreKey = factory.generateCacheKey(applicationInitialize.getAppName(), SSOConstants.JWT_KEYS_LIST);
            Map<String, JwtTokenStore> tokenStore = factory.get(tokenStoreKey);
            if (null != tokenStore) {
                JwtTokenStore jwtTokenStore = tokenStore.get(jwsClaims.getBody().getSubject());
                jwtTokenStore.setLogoutTime(date);
                tokenStore.put(jwsClaims.getBody().getSubject(), jwtTokenStore);
            }
            logger.info("用户:{}登出成功,登出时间:{}", jwsClaims.getBody().getSubject(), DateUtil.toDateTimeDash(date));
            factory.getCacheClient().remove(Access_Jwt_Cache);
        }
    }

}
