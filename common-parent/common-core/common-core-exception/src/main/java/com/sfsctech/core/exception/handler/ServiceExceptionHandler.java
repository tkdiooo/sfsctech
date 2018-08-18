package com.sfsctech.core.exception.handler;

import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.ex.GenericException;
import com.sfsctech.core.exception.enums.BaseExceptionTipsEnum;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.core.exception.ex.RpcException;
import com.sfsctech.core.exception.ex.VerifyException;
import com.sfsctech.support.common.util.ResponseUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 服务端异常处理
 *
 * @author 张麒 2018/8/18.
 * @version Description:
 */
@ControllerAdvice
public class ServiceExceptionHandler {

    protected final static Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public void runtimeExceptionHandler(HttpServletResponse response, BizException e) throws IOException {
        logger.info("业务异常:[{}]", e.getMessage());
        RpcResult result = new RpcResult();
        result.setMessage(e.getMessage());
        result.setStatus(RpcConstants.Status.Failure);
        ResponseUtil.writeJson(result, response);
    }

    /**
     * RPC异常
     */
    @ExceptionHandler(RpcException.class)
    public void runtimeExceptionHandler(HttpServletResponse response, RpcException e) throws IOException {
        logger.info("RPC异常:[{}]", ThrowableUtil.getStackTraceMessage(e), e);
        RpcResult result = new RpcResult();
        result.setMessage(e.getMessage());
        result.setStatus(RpcConstants.Status.RpcError);
        ResponseUtil.writeJson(result, response);
    }

    /**
     * 校验异常
     */
    @ExceptionHandler(VerifyException.class)
    public void runtimeExceptionHandler(HttpServletResponse response, VerifyException e) throws IOException {
        logger.warn("校验异常:[{}]", e.getMessage());
        RpcResult result = new RpcResult();
        result.setMessage(e.getMessage());
        result.setStatus(RpcConstants.Status.RequestEntityTooLarge);
        result.addAttach(CommonConstants.MESSAGES_DETAILS, e.getResult());
        ResponseUtil.writeJson(result, response);
    }

    /**
     * 服务系统异常
     */
    @ExceptionHandler(Exception.class)
    public void runtimeExceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        logger.error("服务系统异常:[{}]", ThrowableUtil.getStackTraceMessage(e), e);
        ResponseUtil.writeJson(new RpcResult(RpcConstants.Status.ServerError), response);
    }
}
