package com.sfsctech.core.base.ex.enums;

import com.sfsctech.core.base.enums.BaseEnum;

/**
 * Class ExceptionLevelEnum
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public enum ExceptionLevelEnum implements BaseEnum<Integer, String> {

    Info(1, "信息"),
    Warn(3, "警告"),
    Error(5, "异常"),
    Fatal(7, "毁灭性错误");

    private Integer code;
    private String description;

    ExceptionLevelEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public static String getValueByKey(Integer key) {
        return BaseEnum.findValue(values(), key);
    }

    public static Integer getKeyByValue(String value) {
        return BaseEnum.findKey(values(), value);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
