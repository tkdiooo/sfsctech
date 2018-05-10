package com.sfsctech.common.core.exception.enums;

import com.sfsctech.common.core.base.ex.enums.ExceptionLevelEnum;
import com.sfsctech.common.core.base.ex.ExceptionTips;
import com.sfsctech.common.core.base.ex.enums.ExceptionTypeEnum;

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

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
