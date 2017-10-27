package com.sfsctech.constants;

import com.sfsctech.constants.inf.IEnum;

/**
 * Class UIConstants
 *
 * @author 张麒 2017/10/24.
 * @version Description:
 */
public class UIConstants {

    public enum Operation implements IEnum<String, String> {

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
        public String getContent() {
            return value;
        }

    }

    public static class DataTable {

        public static String getStatus(int status) {
            switch (status) {
                case 0:
                    return "<span class='label label-danger'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
                case 1:
                    return "<span class='label label-success'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
                case 2:
                    return "<span class='label label-warning'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
                case 3:
                    return "<span class='label label-primary'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
                case 4:
                    return "<span class='label label-info'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
                case 5:
                    return "<span class='label bg-purple'>" + StatusConstants.Status.getValueByKey(status) + "</span>";
            }
            return "";
        }

    }
}
