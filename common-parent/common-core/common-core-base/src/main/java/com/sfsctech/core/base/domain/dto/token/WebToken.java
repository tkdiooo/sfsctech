//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.core.base.domain.dto.token;

import java.io.Serializable;

public class WebToken implements Serializable {
    private static final long serialVersionUID = 4013256474479325372L;
    private Header header;
    private Payload payload;
    private String signature;

    public WebToken() {
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
