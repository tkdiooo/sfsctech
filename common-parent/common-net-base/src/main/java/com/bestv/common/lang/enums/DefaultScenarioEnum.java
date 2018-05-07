//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.enums;

import com.bestv.common.config.CommonConfig;
import com.bestv.common.config.ConfigKeyEnum;

enum DefaultScenarioEnum implements CommonScenario<DefaultScenarioEnum> {
    COMMON("0000", "通用打印场景");

    private String code;
    private String description;

    private DefaultScenarioEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getAppCode() {
        return (String)CommonConfig.getProperty(ConfigKeyEnum.APP_CODE);
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
