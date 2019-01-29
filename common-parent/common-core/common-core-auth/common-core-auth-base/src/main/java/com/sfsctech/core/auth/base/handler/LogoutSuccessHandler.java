package com.sfsctech.core.auth.base.handler;

import com.sfsctech.support.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class LogoutSuccessHandler
 *
 * @author 张麒 2019-1-29.
 * @version Description:
 */
public class LogoutSuccessHandler extends BaseSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    private String targetUrl;

    public LogoutSuccessHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("用户：{}登出成功!用户IP：{}", ((User) authentication.getPrincipal()).getUsername(), ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
        logger.info("登录请求url：{}", HttpUtil.getFullUrl(request));
        super.transfer(request, response, targetUrl);

    }
}
