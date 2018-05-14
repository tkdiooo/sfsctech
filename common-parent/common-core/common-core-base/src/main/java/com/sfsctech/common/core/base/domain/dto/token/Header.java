//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.core.base.domain.dto.token;

import java.io.Serializable;

public class Header implements Serializable {
    private static final long serialVersionUID = -4965437237438447247L;
    private String tokenType;
    private String encryptType;

    public Header() {
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }
}
