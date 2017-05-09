package com.sfsctech.common.base.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Class BaseExceptionHandler
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
public abstract class BaseExceptionHandler {

    public final Logger logger = LoggerFactory.getLogger("logger.debug");

    protected ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, JSONObject json, String viewName, HttpStatus status, Exception ex) {
        // 记录异常日志
        System.out.println(JSON.toJSONString(json));
        logger.debug("异常信息：" + JSON.toJSONString(json));
//        String extMessage = ThrowableUtil.getRootMessage(ex);
//        String stackTrace = ThrowableUtil.getStackTraceAsString(ex);
        if (HttpUtil.isAjaxRequest(request)) {
            return handleAjaxError(response, json, status);
        }
        return handleViewError(request.getRequestURL().toString(), json, viewName, status);
    }

    protected ModelAndView handleViewError(String url, Map<String, Object> model, String viewName, HttpStatus status) {
        ModelAndView mav = new ModelAndView(viewName, model, status);
        mav.addObject("url", url);
        mav.addObject("timestamp", new Date());
        return mav;
    }

    protected ModelAndView handleAjaxError(HttpServletResponse response, JSONObject json, HttpStatus status) {
        response.setStatus(status.value());
        try {
            ResponseUtil.writeJson(json, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
