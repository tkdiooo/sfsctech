//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import com.bestv.common.util.CommonUtil;

public enum InfoType implements BaseEnum<InfoType> {
    SYSTEM("0", "系统"),
    BIZ("1", "业务"),
    UNKNOWN("9", "未知");

    private String code;
    private String description;

    private InfoType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static InfoType getByCode(String code) {
        return (InfoType)CommonUtil.getByCode(code, InfoType.class);
    }
}
