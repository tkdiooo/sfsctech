package com.sfsctech.common.util;

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
        Throwable t = getRootCause(e);
        if (t == null) {
            return defaults;
        }
        String msg = t.getLocalizedMessage();
        return StringUtil.isEmpty(msg) ? defaults : msg;
    }
}
