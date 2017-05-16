package com.sfsctech.common.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.base.handler.BaseExceptionHandler;
import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.I18NConstants;
import com.sfsctech.common.exception.excp.BizException;
import com.sfsctech.common.exception.excp.VerifyException;
import com.sfsctech.common.exception.util.BaseExceptionUtil;
import com.sfsctech.common.spring.properties.Application;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class GlobalExceptionHandler
 *
 * @author 张麒 2017/3/29.
 * @version Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @Autowired
    private Application application;

    /**
     * 404异常捕获
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404Error(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(ex));
        // Ajax请求
        if (HttpUtil.isAjaxRequest(request)) {
//            return handleAjaxError(response, json, status);
        }
        return handleError(request, response, json, CommonConstants.VIEW_404, HttpStatus.NOT_FOUND, ex);
    }

    /**
     * 文件上传异常捕获
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, MaxUploadSizeExceededException ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, ResourceUtil.getMessage(I18NConstants.Tips.ExceptionUpload, application.getMaxFileSize()));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * 业务异常捕获
     */
    @ExceptionHandler(BizException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, BizException ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, ResourceUtil.getMessage(ex.getTips(), ex.getParams()));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * 校验异常捕获
     */
    @ExceptionHandler(VerifyException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, VerifyException ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(ex));
        json.put(CommonConstants.MESSAGES_DETAILS, ex.getResult());
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * 运行异常捕获
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(ex));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * 检查异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(ex));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
