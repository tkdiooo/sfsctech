package com.sfsctech.configurer;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.base.exception.BizException;
import com.sfsctech.base.exception.RpcException;
import com.sfsctech.base.exception.VerifyException;
import com.sfsctech.base.result.BaseResult;
import com.sfsctech.common.util.HttpUtil;
import com.sfsctech.common.util.ResourceUtil;
import com.sfsctech.common.util.ThrowableUtil;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.I18NConstants;
import com.sfsctech.constants.RpcConstants.Status;
import com.sfsctech.exception.handler.BaseExceptionHandler;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
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
    private MultipartProperties multipart;

    /**
     * 业务异常捕获
     */
    @ExceptionHandler(BizException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, BizException e) {
        BaseResult result = new BaseResult(false, Status.Failure, ThrowableUtil.getRootMessage(e));
        logger.info("业务异常捕获：" + result.getMessages());
        return handleError(request, response, result, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * RPC异常捕获
     */
    @ExceptionHandler(RpcException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, RpcException e) {
        BaseResult result = new BaseResult(false, Status.PayloadTooLarge, ThrowableUtil.getRootMessage(e));
        logger.info("RPC异常捕获：" + result.getMessages() + "--" + e.getCause().toString());
        return handleError(request, response, result, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 校验异常捕获
     */
    @ExceptionHandler(VerifyException.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, VerifyException e) {
        BaseResult result = new BaseResult(false, Status.Failure, ThrowableUtil.getRootMessage(e));
        result.addAttach(CommonConstants.MESSAGES_DETAILS, e.getResult());
        logger.warn("校验异常捕获：" + result.getMessages());
        return handleError(request, response, result, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 404异常捕获
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404Error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        JSONObject json = new JSONObject();
        json.put("message", ThrowableUtil.getRootMessage(e));
        json.put("request ip", HttpUtil.getRequestIP(request));
        logger.warn("404异常捕获：" + json.toJSONString());
        BaseResult result = new BaseResult(false, Status.NotFound, ResourceUtil.getMessage(I18NConstants.Tips.Exception404));
        return handleError(request, response, result, CommonConstants.VIEW_404, HttpStatus.NOT_FOUND);
    }

    /**
     * 文件上传异常捕获
     */
    @ExceptionHandler({MultipartException.class})
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, MultipartException e) {
        logger.warn("文件上传异常捕获：" + ThrowableUtil.getStackTraceMessage(e));
        BaseResult result = new BaseResult(false, Status.PayloadTooLarge, ResourceUtil.getMessage(I18NConstants.Tips.ExceptionUpload, multipart.getMaxFileSize()));
        return handleError(request, response, result, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 系统异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("系统异常捕获：" + ThrowableUtil.getStackTraceMessage(e), e);
        BaseResult result = new BaseResult(false, Status.ServerError, ResourceUtil.getMessage(I18NConstants.Tips.ExceptionService));
        return handleError(request, response, result, CommonConstants.VIEW_500, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
