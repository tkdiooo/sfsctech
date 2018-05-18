//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.core.base.domain.dto;


import java.io.Serializable;

public class EnvContext implements Serializable {
    private static final long serialVersionUID = -8117852840797255666L;

    private String traceNo;

    private String ipAddress;

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
