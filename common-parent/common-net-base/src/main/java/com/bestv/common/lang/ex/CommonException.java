//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

public interface CommonException<C extends CommonErrorCode> {
    C getErrorCode();

    void setErrorCode(C var1);

    String getMessage();

    String getExtraMessage();

    void setExtraMessage(String var1);

    StackTraceElement[] getStackTrace();

    void setStackTrace(StackTraceElement[] var1);
}
