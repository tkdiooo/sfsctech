package com.sfsctech.common.constants;

import com.sfsctech.common.inf.IEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class StatusConstants
 *
 * @author 张麒 2016年3月27日
 * @version Description：状态常量类
 */
public class StatusConstants {

    public enum YesNo implements IEnum<Boolean, String> {

        Yes(true, "是"),
        No(false, "否");

        YesNo(Boolean key, String value) {
            this.key = key;
            this.value = value;
        }

        private Boolean key;
        private String value;

        @Override
        public Boolean getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static String getValueByKey(Boolean key) {
            return IEnum.findValue(values(), key);
        }

        public static Boolean getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }
    }

    /**
     * Class DataStatus
     *
     * @author 张麒 2016年3月28日
     * @version Description：状态枚举
     */
    public enum Status implements IEnum<Integer, String> {

        /**
         * 有效
         */
        VALID(1, "有效"),

        /**
         * 失效
         */
        INVALID(0, "失效");

        Status(int key, String value) {
            this.key = key;
            this.value = value;
        }

        private int key;
        private String value;

        @Override
        public Integer getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }

        private static List<IEnum<Integer, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<IEnum<Integer, String>> getOptions() {
            return options;
        }
    }

    /**
     * Class DictDefine
     *
     * @author 张麒 2016年3月28日
     * @version Description：字典界限
     */
    public enum DictDefine implements IEnum<Integer, String> {

        /**
         * 系统级
         */
        SYS_LEVEL(0, "系统级"),

        /**
         * 业务级
         */
        BIZ_LEVEL(1, "业务级");

        DictDefine(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private Integer key;
        private String value;

        @Override
        public Integer getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            return IEnum.findValue(values(), key);
        }

        public static Integer getKeyByValue(String value) {
            return IEnum.findKey(values(), value);
        }
    }

}
