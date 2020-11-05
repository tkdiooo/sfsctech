package com.sfsctech.dubbo.base.filter;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.ValidatorResult;
import com.sfsctech.core.base.ex.BaseException;
import com.sfsctech.core.base.ex.VerifyException;
import com.sfsctech.core.exception.enums.VerifyExceptionTipsEnum;
import com.sfsctech.core.spring.util.ValidatorUtil;
import com.sfsctech.core.logger.util.TraceNoUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.reflect.Method;

/**
 * Class ExceptionFilter
 *
 * @author 张麒 2017/5/24.
 * @version Description:
 */
public class ExceptionFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        MDC.put(TraceNoUtil.TRACE_ID, invocation.getAttachment(TraceNoUtil.TRACE_ID));
        logger.info("RPC调用：{IP：" + RpcContext.getContext().getRemoteHost() + ", service：" + invoker.getInterface().getName() + ", method：" + invocation.getMethodName() + "}");
        // 根据参数类型判断是否需要验证参数
        Class<?>[] parameterTypes = invocation.getParameterTypes();
        Object[] arguments = invocation.getArguments();
        for (int i = 0; i < parameterTypes.length; i++) {
            // 请求参数不为空
            if (null != arguments[i]) {
                Object argument = getArgument(arguments[i]);
                if (null != argument) {
                    ValidatorResult result = ValidatorUtil.validate(argument);
                    if (result.hasErrors()) {
                        // 数据校验异常
                        logger.error("数据校验异常：{ " +
                                "IP：" + RpcContext.getContext().getRemoteHost() + ", " +
                                "service：" + invoker.getInterface().getName() + ", " +
                                "method：" + invocation.getMethodName() + ", " +
                                "exception: " + VerifyException.class.getName() + ": " + result.getMessages() + "}");
                        return new RpcResult(new VerifyException(VerifyExceptionTipsEnum.ParameterWrong, result));
                    }
                }
            }
        }
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            try {
                Throwable exception = result.getException();
                String logInfo = "Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + exception.getClass().getName() + ": " + ThrowableUtil.getRootMessage(exception);

                // 是自定义异常，直接抛出
                if (exception instanceof BaseException) {
                    logger.error(logInfo);
                    return result;
                }

                // 如果是checked异常，直接抛出
                if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                    return result;
                }
                // 在方法签名上有声明，直接抛出
                try {
                    Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    Class<?>[] exceptionClassses = method.getExceptionTypes();
                    for (Class<?> exceptionClass : exceptionClassses) {
                        if (exception.getClass().equals(exceptionClass)) {
                            return result;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    return result;
                }

                // 未在方法签名上定义的异常，在服务器端打印ERROR日志
                logger.error(logInfo, exception);

                // 异常类和接口类在同一jar包里，直接抛出
                String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                    return result;
                }

                // 是JDK自带的异常，直接抛出
                String className = exception.getClass().getName();
                if (className.startsWith("java.") || className.startsWith("javax.")) {
                    return result;
                }
                // 是Dubbo本身的异常，直接抛出
                if (exception instanceof RpcException) {
                    return result;
                }

                // 否则，包装成RuntimeException抛给客户端
                return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
            } catch (Throwable e) {
                logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                        + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                        + ", exception: " + e.getClass().getName() + ": " + ThrowableUtil.getRootMessage(e), e);
                return result;
            }
        }
        return result;
    }

    private Object getArgument(Object argument) {
        // 参数是pagingInfo类，校验包含的参数
        if (PagingInfo.class.equals(argument.getClass()) && null != ((PagingInfo) argument).getCondition()) {
            Object condition = ((PagingInfo) argument).getCondition();
            // 参数继承 com.sfsctech.common.base.model.BaseDto
            if (BaseDto.class.equals(condition.getClass().getSuperclass())) {
                return condition;
            }
        }
        // 参数继承 com.sfsctech.common.base.model.BaseDto
        else if (BaseDto.class.equals(argument.getClass().getSuperclass())) {
            return argument;
        }
        return null;
    }
}
