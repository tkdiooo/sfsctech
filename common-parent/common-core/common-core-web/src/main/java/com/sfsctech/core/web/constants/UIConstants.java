package com.sfsctech.core.web.constants;

import com.sfsctech.core.base.enums.BaseEnum;

/**
 * Class UIConstants
 *
 * @author 张麒 2017/10/24.
 * @version Description:
 */
public class UIConstants {

    public enum Operation implements BaseEnum<String, String> {

        Added("operation", "新增"),
        Editor("operation", "编辑");

        Operation(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        @Override
        public String getCode() {
            return key;
        }

        @Override
        public String getDescription() {
            return value;
        }
    }

}
