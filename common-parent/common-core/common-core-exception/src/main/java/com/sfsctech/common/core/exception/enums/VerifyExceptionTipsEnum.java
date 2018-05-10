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
public enum VerifyExceptionTipsEnum implements ExceptionTips<String, String> {

    LoginWrong("00001", "账号或密码错误", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    LoginUnrepeatable("00002", "当前账号已经登录，不能重复登录", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountNoExist("00003", "账号不存在", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountRemove("00004", "账号已被删除", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountDisabled("00005", "账号已被禁用", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountLocked("00006", "账号已被锁定", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    ParameterWrong("00007", "参数验证失败", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn);

    private String code;
    private String description;
    private ExceptionTypeEnum type;
    private ExceptionLevelEnum level;

    VerifyExceptionTipsEnum(String code, String description, ExceptionTypeEnum type, ExceptionLevelEnum level) {
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
