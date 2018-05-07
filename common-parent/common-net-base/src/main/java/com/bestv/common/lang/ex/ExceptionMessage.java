//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

class ExceptionMessage {
    private String errorCode;
    private String normalMessage;
    private String extraMessage;

    ExceptionMessage() {
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getNormalMessage() {
        return this.normalMessage;
    }

    public void setNormalMessage(String normalMessage) {
        this.normalMessage = normalMessage;
    }

    public String getExtraMessage() {
        return this.extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public String toStringWithoutClassName() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("errorCode='").append(this.errorCode).append('\'');
        sb.append(", normalMessage='").append(this.normalMessage).append('\'');
        sb.append(", extraMessage='").append(this.extraMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ExceptionMessage{");
        sb.append("errorCode='").append(this.errorCode).append('\'');
        sb.append(", normalMessage='").append(this.normalMessage).append('\'');
        sb.append(", extraMessage='").append(this.extraMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
