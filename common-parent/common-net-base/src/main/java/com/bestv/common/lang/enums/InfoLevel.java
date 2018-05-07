//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import com.bestv.common.util.CommonUtil;

public enum InfoLevel implements BaseEnum<InfoLevel> {
    INFO("1", "信息"),
    WARN("3", "警告"),
    ERROR("5", "异常"),
    FATAL("7", "毁灭性错误");

    private String code;
    private String description;

    private InfoLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static InfoLevel getByCode(String code) {
        return (InfoLevel)CommonUtil.getByCode(code, InfoLevel.class);
    }
}
