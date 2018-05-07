//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = 7106511180785473437L;
    private String errorCode;
    private StackTraceElement[] stackTraceElements;

    public ErrorInfo() {
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public StackTraceElement[] getStackTraceElements() {
        return this.stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }
}
