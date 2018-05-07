//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.dto;

import com.bestv.common.dto.token.WebToken;
import java.io.Serializable;

public class EnvContext implements Serializable {
    private static final long serialVersionUID = -8117852840797255666L;
    private TraceInfo traceInfo;
    private WebToken webToken;
    private String ipAddress;

    public EnvContext() {
    }

    public WebToken getWebToken() {
        return this.webToken;
    }

    public void setWebToken(WebToken webToken) {
        this.webToken = webToken;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public TraceInfo getTraceInfo() {
        return this.traceInfo;
    }

    public void setTraceInfo(TraceInfo traceInfo) {
        this.traceInfo = traceInfo;
    }
}
