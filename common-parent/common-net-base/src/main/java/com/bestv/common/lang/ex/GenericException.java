//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

import com.bestv.common.util.ErrorContextBuilder;
import com.bestv.common.util.ScenarioHolder;

public class GenericException extends RuntimeException implements CommonException<CommonErrorCode> {
    private static final long serialVersionUID = -6172931801902534206L;
    protected CommonErrorCode errorCode;
    protected String extraMessage;

    public GenericException(CommonErrorCode errorCode) {
        super(assembleExceptionMessage(errorCode, (String)null));
        this.errorCode = errorCode;
        this.extraMessage = errorCode.getDescription();
    }

    public GenericException(CommonErrorCode errorCode, Throwable cause) {
        super(assembleExceptionMessage(errorCode, (String)null), cause);
        this.errorCode = errorCode;
        this.extraMessage = errorCode.getDescription();
    }

    public GenericException(CommonErrorCode errorCode, String extraMessage) {
        super(assembleExceptionMessage(errorCode, extraMessage));
        this.errorCode = errorCode;
        this.extraMessage = extraMessage;
    }

    public GenericException(CommonErrorCode errorCode, String extraMessage, Throwable cause) {
        super(assembleExceptionMessage(errorCode, extraMessage), cause);
        this.errorCode = errorCode;
        this.extraMessage = extraMessage;
    }

    private static String assembleExceptionMessage(CommonErrorCode errorCode, String extraMessage) {
        String errorCodeStr = ErrorContextBuilder.buildErrorCode(ScenarioHolder.get(), errorCode);
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setErrorCode(errorCodeStr);
        exceptionMessage.setNormalMessage(errorCode.getDescription());
        exceptionMessage.setExtraMessage(extraMessage);
        return exceptionMessage.toStringWithoutClassName();
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
