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
public enum VerifyExceptionTipsEnum implements ExceptionTips<String, String> {

    LoginWrong("tips.login.wrong", "账号或密码错误", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    LoginUnrepeatable("tips.login.unrepeatable", "当前账号已经登录，不能重复登录", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountNoExist("tips.login.account.noexist", "账号不存在", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountRemove("tips.login.account.remove", "账号已被删除", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountDisabled("tips.login.account.disabled", "账号已被禁用", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    AccountLocked("tips.login.account.locked", "账号已被锁定", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    CsrfWrong("tips.csrf.wrong", "Csrf请求拦截", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn),
    CsrfError("tips.csrf.error", "Csrf攻击拦截", ExceptionTypeEnum.System, ExceptionLevelEnum.Error),
    ParameterWrong("tips.exception.validator", "数据校验错误", ExceptionTypeEnum.Business, ExceptionLevelEnum.Warn);

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
