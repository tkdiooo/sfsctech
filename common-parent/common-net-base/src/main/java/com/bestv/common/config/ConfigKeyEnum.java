//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.config;

import com.bestv.common.lang.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

public enum ConfigKeyEnum implements BaseEnum<ConfigKeyEnum> {
    APP_CODE("biz.appCode", "业务线编码", false),
    ASSERTUTIL_EXCEPTION_CLASS_NAME("assertutil.exception.class.name", "AssertUtil工具使用的异常类名", true),
    BIZ_ERROR_BUILDER_ERROR_CODE_PREFIX("biz.error.builder.errorcode.prefix", "异常码前缀", true),
    BIZ_ERROR_BUILDER_ERROR_CODE_VERSION("biz.error.builder.errorcode.version", "异常码版本号", true),
    BIZ_ERROR_BUILDER_STACK_DEPTH("biz.error.builder.stack.depth", Integer.class, "异常信息堆栈深度, 代表屏蔽前几层异常堆栈信息", true),
    BIZ_ERROR_BUILDER_STACK_LENGTH("biz.error.builder.stack.length", Integer.class, "异常信息堆栈长度", true),
    TEST_USE_MOCK("test.use.mock", Boolean.class, "测试使用mock", true);

    private String code;
    private Class propertyType;
    private String description;
    private boolean allowNull;

    private ConfigKeyEnum(String code, String description, boolean allowNull) {
        this.code = code;
        this.propertyType = String.class;
        this.description = description;
        this.allowNull = allowNull;
    }

    private ConfigKeyEnum(String code, Class propertyType, String description, boolean allowNull) {
        this.code = code;
        this.propertyType = propertyType;
        this.description = description;
        this.allowNull = allowNull;
    }

    public static ConfigKeyEnum getByCode(String code) {
        ConfigKeyEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ConfigKeyEnum configKeyEnum = var1[var3];
            if (StringUtils.equals(configKeyEnum.getCode(), code)) {
                return configKeyEnum;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public <T> Class<T> getPropertyType() {
        return this.propertyType;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isAllowNull() {
        return this.allowNull;
    }
}
