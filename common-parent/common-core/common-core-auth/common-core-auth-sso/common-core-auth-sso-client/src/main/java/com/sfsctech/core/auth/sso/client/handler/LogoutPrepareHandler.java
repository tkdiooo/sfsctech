package com.sfsctech.core.auth.sso.client.handler;

import com.sfsctech.core.auth.base.handler.BaseSuccessHandler;
import com.sfsctech.core.auth.sso.base.token.extractor.TokenExtractor;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class LogoutHandler
 *
 * @author 张麒 2019-4-17.
 * @version Description:
 */
public class LogoutPrepareHandler extends BaseSuccessHandler implements LogoutHandler {

    private final Logger logger = LoggerFactory.getLogger(LogoutPrepareHandler.class);
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final String logoutUrl;

    public LogoutPrepareHandler(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // redirect方式跳转登录页面
            logger.info("redirect登出操作：{}", logoutUrl);
            redirectStrategy.sendRedirect(request, response, logoutUrl);
        } catch (IOException e) {
            logger.error(ThrowableUtil.getRootMessage(e), e);
        }
    }
}
