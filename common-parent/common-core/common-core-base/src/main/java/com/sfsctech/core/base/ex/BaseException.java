package com.sfsctech.core.base.ex;

/**
 * Class BaseException
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public interface BaseException<T extends ExceptionTips<String, String>> {

    T getTips();

    void setTips(T tips);

    String getMessage();

    StackTraceElement[] getStackTrace();

    void setStackTrace(StackTraceElement[] var1);
}
