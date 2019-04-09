package com.sfsctech.core.auth.base.handler;

import com.sfsctech.core.auth.base.constants.SessionConstants;
import com.sfsctech.core.base.constants.LabelConstants;
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
import java.net.URLEncoder;

/**
 * 鉴权失败处理
 * Class LoginFailureHandler
 *
 * @author 张麒 2019-1-29.
 * @version Description:
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandler.class);
    private String defaultFailureUrl;

    public AuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("用户认证错误!错误原因：{}", exception.getMessage());
        String requestUrl = HttpUtil.getFullUrl(request);
        logger.info("登录请求url：{}", requestUrl);

        // ajax访问
        if (HttpUtil.isAjaxRequest(request)) {
            ActionResult<String> result = ActionResult.forFailure(request.getLocale());
            result.setMessage(exception.getMessage());
            result.addAttach(SessionConstants.PARAM_FROM_URL, requestUrl);
            logger.info("以json方式返回result：{}", result.toString());
            ResponseUtil.writeJson(result, response);
        } else {
            String redirectUrl = defaultFailureUrl + LabelConstants.QUESTION + SessionConstants.PARAM_FROM_URL + LabelConstants.EQUAL + URLEncoder.encode(requestUrl, LabelConstants.UTF8);
            logger.info("以redirect方式返回路径：{}", redirectUrl);
            super.setDefaultFailureUrl(redirectUrl);
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
