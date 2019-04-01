package com.sfsctech.core.auth.base.handler;

import com.sfsctech.core.auth.base.constants.SessionConstants;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Class BaseSuccessHandler
 *
 * @author 张麒 2019-1-29.
 * @version Description:
 */
public abstract class BaseSuccessHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private RequestCache requestCache = new HttpSessionRequestCache();

    protected void init(HttpServletRequest request, Authentication authentication) {
        logger.info("用户：{}登录成功!用户IP：{}", ((User) authentication.getPrincipal()).getUsername(), ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
        logger.info("登录请求url：{}", HttpUtil.getFullUrl(request));

    }

    protected void transfer(HttpServletRequest request, HttpServletResponse response, String targetUrl) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest) {
            targetUrl = savedRequest.getRedirectUrl();
            logger.info("重定向url: {}", targetUrl);
        } else if (StringUtil.isNotBlank(request.getHeader("Referer"))) {
            targetUrl = request.getHeader("Referer");
            targetUrl = URLDecoder.decode(targetUrl.substring(targetUrl.lastIndexOf(LabelConstants.EQUAL) + 1), LabelConstants.UTF8);
            logger.info("重定向url: {}", targetUrl);
        }
        // ajax访问
        if (HttpUtil.isAjaxRequest(request)) {
            ActionResult<String> result = ActionResult.forSuccess(request.getLocale());
            result.addAttach(SessionConstants.PARAM_FROM_URL, targetUrl);
            logger.info("ajax访问，以json方式返回result：{}", result.toString());
            ResponseUtil.writeJson(result, response);
        } else {
            logger.info("form提交，以redirect方式返回url：{}", targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
    }

}
