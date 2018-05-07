//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log;

import com.bestv.common.lang.enums.BaseEnum;

public enum LoggerTypeEnum implements BaseEnum<LoggerTypeEnum> {
    SERVER("S", "服务端"),
    CLIENT("C", "客户端");

    private String code;
    private String description;

    private LoggerTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
