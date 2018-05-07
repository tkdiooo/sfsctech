//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

import com.bestv.common.lang.enums.InfoLevel;
import com.bestv.common.lang.enums.InfoType;

enum DefaultErrorCodeEnum implements CommonErrorCode<DefaultErrorCodeEnum> {
    UNKNOWN_ERROR("999", "未知异常", InfoType.SYSTEM, InfoLevel.ERROR);

    private String code;
    private String description;
    private InfoType infoType;
    private InfoLevel infoLevel;

    private DefaultErrorCodeEnum(String code, String description, InfoType infoType, InfoLevel infoLevel) {
        this.code = code;
        this.description = description;
        this.infoType = infoType;
        this.infoLevel = infoLevel;
    }

    public InfoType getInfoType() {
        return this.infoType;
    }

    public InfoLevel getInfoLevel() {
        return this.infoLevel;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
