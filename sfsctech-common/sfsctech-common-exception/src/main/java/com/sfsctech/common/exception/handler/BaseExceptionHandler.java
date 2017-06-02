package com.sfsctech.common.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.ResponseUtil;
import com.sfsctech.common.util.StringUtil;
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

    protected ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, JSONObject json, String viewName, HttpStatus status) {
        System.out.println(json.toJSONString());
        String ret_url = request.getHeader("Referer");
        // 如果上一次请求路径为空，跳转首页。(首页需要配置)
        if (StringUtil.isBlank(ret_url)) {
            ret_url = "http://localhost:8081/jspdemo/";
        }
        if (HttpUtil.isAjaxRequest(request)) {
            json.put("url", ret_url);
            return handleAjaxError(response, json, status);
        }
        return handleViewError(ret_url, json, viewName, status);
    }

    private ModelAndView handleViewError(String url, Map<String, Object> model, String viewName, HttpStatus status) {
        ModelAndView mav = new ModelAndView(viewName, model, status);
        mav.addObject("url", url);
        mav.addObject("timestamp", new Date());
        return mav;
    }

    private ModelAndView handleAjaxError(HttpServletResponse response, JSONObject json, HttpStatus status) {
        response.setStatus(status.value());
        try {
            ResponseUtil.writeJson(json, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
