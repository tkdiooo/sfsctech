package com.sfsctech.common.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.base.handler.BaseExceptionHandler;
import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.I18NConstants;
import com.sfsctech.common.exception.excp.BizException;
import com.sfsctech.common.exception.excp.VerifyException;
import com.sfsctech.common.exception.util.BaseExceptionUtil;
import com.sfsctech.common.spring.properties.AppConfig;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.ResourceUtil;
import com.sfsctech.common.util.ThrowableUtil;
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
    private AppConfig appConfig;

    /**
     * 业务异常捕获
     */
    @ExceptionHandler(BizException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, BizException e) throws Exception {
        logger.info("业务异常捕获：" + ThrowableUtil.getStackTraceMessage(e));
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(e));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 校验异常捕获
     */
    @ExceptionHandler(VerifyException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, VerifyException e) throws Exception {
        logger.warn("校验异常捕获：" + ThrowableUtil.getStackTraceMessage(e));
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, BaseExceptionUtil.getRootMessage(e));
        json.put(CommonConstants.MESSAGES_DETAILS, e.getResult());
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 404异常捕获
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404Error(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        JSONObject json = new JSONObject();
        json.put("message", ThrowableUtil.getRootMessage(e));
        json.put("request ip", HttpUtil.getRequestIP(request));
        logger.warn("404异常捕获：" + json.toJSONString() + "");
        json.clear();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, ResourceUtil.getMessage(I18NConstants.Tips.Exception404));
        return handleError(request, response, json, CommonConstants.VIEW_404, HttpStatus.NOT_FOUND);
    }

    /**
     * 文件上传异常捕获
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, MaxUploadSizeExceededException e) throws Exception {
        logger.warn("文件上传异常捕获：" + ThrowableUtil.getStackTraceMessage(e));
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, ResourceUtil.getMessage(I18NConstants.Tips.ExceptionUpload, appConfig.MULTIPART_MAX_FILE_SIZE));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 系统异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("系统异常捕获：" + ThrowableUtil.getStackTraceMessage(e));
        JSONObject json = new JSONObject();
        json.put(CommonConstants.SUCCESS, false);
        json.put(CommonConstants.MESSAGES, ResourceUtil.getMessage(I18NConstants.Tips.ExceptionService));
        return handleError(request, response, json, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
