package com.sfsctech.core.auth.base.handler;

import com.sfsctech.core.auth.base.constants.SessionConstants;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class BaseSuccessHandler
 *
 * @author 张麒 2019-1-29.
 * @version Description:
 */
public abstract class BaseSuccessHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected void transfer(HttpServletRequest request, HttpServletResponse response, String targetUrl) throws IOException {
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
