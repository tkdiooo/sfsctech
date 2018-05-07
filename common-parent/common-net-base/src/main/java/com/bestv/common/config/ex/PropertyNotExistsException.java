//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.config.ex;

import com.bestv.common.config.ConfigKeyEnum;

public class PropertyNotExistsException extends RuntimeException {
    public PropertyNotExistsException(ConfigKeyEnum configKeyEnum) {
        super(buildMessage(configKeyEnum));
    }

    private static String buildMessage(ConfigKeyEnum configKeyEnum) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("找不到配置项: ").append(configKeyEnum.getCode());
        return stringBuilder.toString();
    }
}
