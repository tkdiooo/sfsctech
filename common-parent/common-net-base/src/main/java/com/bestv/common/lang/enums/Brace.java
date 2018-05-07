//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import com.bestv.common.util.CommonUtil;

public enum Brace implements BaseEnum<Brace> {
    LEFT_BRACE("001", "{", "左花括号"),
    RIGHT_BRACE("002", "}", "右花括号");

    private String code;
    private String value;
    private String description;

    private Brace(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public static Brace getByCode(String code) {
        return (Brace)CommonUtil.getByCode(code, Brace.class);
    }
}
