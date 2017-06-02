package com.sfsctech.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.common.base.exception.BaseException;
import com.sfsctech.common.base.exception.BizException;
import com.sfsctech.common.base.exception.VerifyException;
import com.sfsctech.common.base.result.ValidatorResult;
import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.constants.I18NConstants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Class ThrowableUtil
 *
 * @author 张麒 2016年3月30日
 * @version Description：
 */
public class ThrowableUtil {

    /**
     * 抛出数据校验异常
     *
     * @param tips   Tips
     * @param params params
     */
    public static void throwVerifyException(I18NConstants.Tips tips, String... params) {
        throw new VerifyException(tips, params);
    }

    /**
     * 抛出数据校验异常
     *
     * @param message message
     * @param params  params
     */
    public static void throwVerifyException(String message, String... params) {
        throw new VerifyException(message, params);
    }

    /**
     * 抛出数据校验异常
     *
     * @param tips   Tips
     * @param result ValidatorResult
     * @param params params
     */
    public static void throwVerifyException(I18NConstants.Tips tips, ValidatorResult result, String... params) {
        throw new VerifyException(tips, result, params);
    }

    /**
     * 抛出数据校验异常
     *
     * @param message message
     * @param result  ValidatorResult
     * @param params  params
     */
    public static void throwVerifyException(String message, ValidatorResult result, String... params) {
        throw new VerifyException(message, result, params);
    }

    /**
     * 抛出业务异常
     *
     * @param tips   Tips
     * @param params params
     */
    public static void throwBizException(I18NConstants.Tips tips, String... params) {
        throw new BizException(tips, params);
    }

    /**
     * 抛出业务异常
     *
     * @param message message
     * @param params  params
     */
    public static void throwBizException(String message, String... params) {
        throw new BizException(message, params);
    }

    /**
     * 抛出运行时异常
     *
     * @param message Message
     */
    public static void throwRuntimeException(String message) {
        throw new RuntimeException(message);
    }

    /**
     * 抛出运行时异常
     *
     * @param e exception
     */
    public static void throwRuntimeException(Exception e) {
        throw convertExceptionToUnchecked(e);
    }

    /**
     * 将exception转换为unchecked exception.
     *
     * @param e exception
     */
    public static RuntimeException convertExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException)
            return new IllegalArgumentException("Reflection Exception.", e);
        else if (e instanceof InvocationTargetException)
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        else if (e instanceof RuntimeException)
            return (RuntimeException) e;
        return new RuntimeException("Unexpected Checked Exception.", e);
    }


    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(Exception e, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = e.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 获取异常的Root Cause
     *
     * @param e Throwable
     * @return Root Cause
     */
    public static Throwable getRootCause(Throwable e) {
        Throwable cause;
        while ((cause = e.getCause()) != null) {
            e = cause;
        }
        return e;
    }


    /**
     * 获取异常的Root Message
     *
     * @param e Throwable
     * @return Root Message
     */
    public static String getRootMessage(Throwable e) {
        return getRootMessage(e, null);
    }

    /**
     * 获取异常的Root Message
     *
     * @param e        Throwable
     * @param defaults Defaults Message
     * @return Root Message
     */
    public static String getRootMessage(Throwable e, String defaults) {
        String msg;
        if (e instanceof BaseException) {
            BaseException ext = (BaseException) e;
            if (null != ext.getTips()) {
                msg = ResourceUtil.getMessage(ext.getTips(), ext.getParams());
            } else {
                msg = ext.getMessage();
            }
        } else {
            Throwable t = getRootCause(e);
            if (t == null) {
                return defaults;
            }
            msg = t.toString();
        }
        return StringUtil.isEmpty(msg) ? defaults : msg;
    }

    /**
     * 获取异常抛出的具体位置
     *
     * @param e Exception
     * @return Exception Message
     */
    public static String getStackTraceMessage(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement traceElement : trace) {
            if (traceElement.getClassName().startsWith("com.sfsctech")) {
                JSONObject stack = (JSONObject) JSONObject.toJSON(traceElement);
                stack.remove("fileName");
                stack.remove("nativeMethod");
                stack.put(CommonConstants.MESSAGES, ThrowableUtil.getRootMessage(e));
                return stack.toJSONString();
            }
        }
        return e.getMessage();
    }
}
