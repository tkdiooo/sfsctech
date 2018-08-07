package com.sfsctech.core.exception.enums;

import com.sfsctech.core.base.ex.ExceptionTips;
import com.sfsctech.core.base.ex.enums.ExceptionLevelEnum;
import com.sfsctech.core.base.ex.enums.ExceptionTypeEnum;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class BaseExceptionTipsEnum
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public enum BaseExceptionTipsEnum implements ExceptionTips<String, String> {

    SystemError("001", "系统异常", ExceptionTypeEnum.System, ExceptionLevelEnum.Error),
    UnknownError("999", "未知异常", ExceptionTypeEnum.System, ExceptionLevelEnum.Error);

    private String code;
    private String description;
    private ExceptionTypeEnum type;
    private ExceptionLevelEnum level;

    BaseExceptionTipsEnum(String code, String description, ExceptionTypeEnum type, ExceptionLevelEnum level) {
        this.code = code;
        this.description = description;
        this.type = type;
        this.level = level;
    }

    @Override
    public ExceptionTypeEnum getType() {
        return this.type;
    }

    @Override
    public ExceptionLevelEnum getLevel() {
        return this.level;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)).setExcludeFieldNames("name", "ordinal").toString();
    }
}
