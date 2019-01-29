package com.sfsctech.core.auth.base.handler;

import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.support.common.util.HttpUtil;
import com.sfsctech.support.common.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class LoginFailureHandler
 *
 * @author 张麒 2019-1-29.
 * @version Description:
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("用户登录错误!错误原因：{}", exception.getMessage());
        logger.info("登录请求url：{}", HttpUtil.getFullUrl(request));

        // ajax访问
        if (HttpUtil.isAjaxRequest(request)) {
            ActionResult<String> result = ActionResult.forFailure(request.getLocale());
            result.setMessage(exception.getMessage());
            logger.info("ajax访问，以json方式返回result：{}", result.toString());
            ResponseUtil.writeJson(result, response);
        } else {
            logger.info("form提交，以redirect方式返回");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
