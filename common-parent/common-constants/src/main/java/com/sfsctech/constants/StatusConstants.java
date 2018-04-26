package com.sfsctech.constants;

import com.sfsctech.constants.inf.IEnum;

import java.util.*;
import java.util.stream.Collectors;

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

        private static Map<Integer, String> columns = options.stream().collect(Collectors.toMap(IEnum::getCode, IEnum::getContent));

        public static Map<Integer, String> getColumns() {
            return columns;
        }
    }

}