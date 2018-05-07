//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import org.apache.commons.lang3.StringUtils;

public enum TokenType implements BaseEnum<TokenType> {
    JWT("JWT", "json web token");

    private String code;
    private String description;

    private TokenType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TokenType getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        } else {
            TokenType[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                TokenType tokenType = var1[var3];
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

    public String getDescription() {
        return this.description;
    }
}
