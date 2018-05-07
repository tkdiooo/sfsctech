//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

public class FastGenericException extends GenericException {
    private static final long serialVersionUID = -6172931801902534206L;

    public FastGenericException(CommonErrorCode errorCode) {
        super(errorCode);
    }

    public FastGenericException(GenericException ex) {
        super(ex.getErrorCode(), ex.getExtraMessage());
    }

    public FastGenericException(CommonErrorCode errorCode, String extraMessage) {
        super(errorCode, extraMessage);
    }

    public Throwable fillInStackTrace() {
        return null;
    }

    public CommonErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(CommonErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getExtraMessage() {
        return this.extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }
}
