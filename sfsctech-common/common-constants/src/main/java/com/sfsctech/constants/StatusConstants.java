package com.sfsctech.constants;

import com.sfsctech.constants.inf.IEnum;

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
        public Boolean getCode() {
            return key;
        }

        @Override
        public String getContent() {
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
         * 删除
         */
        Delete(0, "删除"),

        /**
         * 有效
         */
        Valid(1, "有效"),

        /**
         * 禁用
         */
        Disable(2, "禁用"),

        /**
         * 启用
         */
        Enabled(3, "启用"),

        /**
         * 锁定
         */
        Locked(4, "锁定"),

        /**
         * 解锁
         */
        UnLocked(5, "解锁");

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
        public String getContent() {
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
     * Class Define
     *
     * @author 张麒 2016年3月28日
     * @version Description：界限
     */
    public enum Define implements IEnum<Integer, String> {

        /**
         * 系统字典
         */
        SysDict(0, "系统级"),

        /**
         * 业务字典
         */
        BizDict(1, "业务级");

        Define(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private Integer key;
        private String value;

        @Override
        public Integer getCode() {
            return key;
        }

        @Override
        public String getContent() {
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
