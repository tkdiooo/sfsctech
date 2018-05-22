package com.sfsctech.core.exception.enums;

import com.sfsctech.core.base.ex.ExceptionTips;
import com.sfsctech.core.base.ex.enums.ExceptionLevelEnum;
import com.sfsctech.core.base.ex.enums.ExceptionTypeEnum;

/**
 * Class BaseExceptionTipsEnum
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public enum RpcExceptionTipsEnum implements ExceptionTips<String, String> {

    NetworkError("001", "网络连接错误", ExceptionTypeEnum.System, ExceptionLevelEnum.Error),
    ServiceError("002", "服务器错误", ExceptionTypeEnum.System, ExceptionLevelEnum.Error);

    private String code;
    private String description;
    private ExceptionTypeEnum type;
    private ExceptionLevelEnum level;

    RpcExceptionTipsEnum(String code, String description, ExceptionTypeEnum type, ExceptionLevelEnum level) {
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
