//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import org.apache.commons.lang3.StringUtils;

public enum TokenEncryptType implements BaseEnum<TokenEncryptType> {
    HMAC_SHA256("HS256", "HmacSHA256", "SHA-256 Hash-based message authentication code");

    private String code;
    private String instanceName;
    private String description;

    private TokenEncryptType(String code, String instanceName, String description) {
        this.code = code;
        this.instanceName = instanceName;
        this.description = description;
    }

    public static TokenEncryptType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        } else {
            TokenEncryptType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                TokenEncryptType tokenType = var1[var3];
                if (StringUtils.equals(code, tokenType.getCode())) {
                    return tokenType;
                }
            }

            return null;
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public String getDescription() {
        return this.description;
    }
}
