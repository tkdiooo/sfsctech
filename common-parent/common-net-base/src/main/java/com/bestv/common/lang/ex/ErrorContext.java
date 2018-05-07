//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

import java.io.Serializable;
import java.util.List;

public class ErrorContext implements Serializable {
    private static final long serialVersionUID = 7334293204785772486L;
    private String errorCode;
    private String errorMessage;
    private List<ErrorInfo> errorInfos;

    public ErrorContext() {
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ErrorInfo> getErrorInfos() {
        return this.errorInfos;
    }

    public void setErrorInfos(List<ErrorInfo> errorInfos) {
        this.errorInfos = errorInfos;
    }
}
