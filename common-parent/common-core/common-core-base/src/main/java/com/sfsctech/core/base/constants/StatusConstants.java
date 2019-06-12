package com.sfsctech.core.base.constants;

import com.sfsctech.core.base.enums.BaseEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class StatusConstants
 *
 * @author 张麒 2016年3月27日
 * @version Description：状态常量类
 */
public class StatusConstants {

    public enum YesNo implements BaseEnum<Boolean, String> {

        Yes(true, "是"),
        No(false, "否");

        YesNo(Boolean key, String value) {
            this.key = key;
            this.value = value;
        }

        private Boolean key;
        private String value;

        @Override
        public Boolean getCode() {
            return key;
        }

        @Override
        public String getDescription() {
            return value;
        }

        public static String getValueByKey(Boolean key) {
            return BaseEnum.findValue(key, values());
        }

        public static Boolean getKeyByValue(String value) {
            return BaseEnum.findKey(value, values());
        }
    }

    /**
     * Class DataStatus
     *
     * @author 张麒 2016年3月28日
     * @version Description：状态枚举
     */
    public enum Status implements BaseEnum<Integer, String> {

        /**
         * 已删除
         */
        Delete(0, "已删除"),

        /**
         * 有效
         */
        Valid(1, "有效的"),

        /**
         * 已禁用
         */
        Disable(2, "已禁用"),

        /**
         * 已锁定
         */
        Enabled(3, "已锁定");

        Status(int key, String value) {
            this.key = key;
            this.value = value;
        }

        private int key;
        private String value;

        @Override
        public Integer getCode() {
            return key;
        }

        @Override
        public String getDescription() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            return BaseEnum.findValue(key, values());
        }

        public static Integer getKeyByValue(String value) {
            return BaseEnum.findKey(value, values());
        }

        private static List<BaseEnum<Integer, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<BaseEnum<Integer, String>> getOptions() {
            return options;
        }

        private static Map<Integer, String> columns = options.stream().collect(Collectors.toMap(BaseEnum::getCode, BaseEnum::getDescription));

        public static Map<Integer, String> getColumns() {
            return columns;
        }
    }

}
