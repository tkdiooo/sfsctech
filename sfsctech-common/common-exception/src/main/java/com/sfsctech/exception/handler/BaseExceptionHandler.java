package com.sfsctech.exception.handler;

import com.sfsctech.base.result.BaseResult;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.JsonUtil;
import com.sfsctech.common.util.ResponseUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.ExcludesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Class BaseExceptionHandler
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
public abstract class BaseExceptionHandler {

    protected final static Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    protected ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, BaseResult result, String viewName, HttpStatus status) {
        String ret_url = request.getHeader("Referer");
        // 如果上一次请求路径为空，跳转首页。(首页需要配置)
        if (StringUtil.isBlank(ret_url)) {
            ret_url = ExcludesConstants.CONTEXT_PATH;
        }
        result.addAttach("url", ret_url);
        if (HttpUtil.isAjaxRequest(request)) {
            return handleAjaxError(response, result, status);
        }
        return handleViewError(viewName, result, status);
    }

    private ModelAndView handleViewError(String viewName, BaseResult result, HttpStatus status) {
        System.out.println(JsonUtil.toJSONString(result));
        ModelAndView mav = new ModelAndView(viewName, new HashMap<>(), status);
        mav.addObject("result", result);
        mav.addObject("timestamp", new Date());
        return mav;
    }

    private ModelAndView handleAjaxError(HttpServletResponse response, BaseResult result, HttpStatus status) {
        System.out.println(JsonUtil.toJSONString(result));
        response.setStatus(status.value());
        try {
            ResponseUtil.writeJson(result, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
