package com.sfsctech.common.core.base.constants;

import com.sfsctech.common.core.base.enums.BaseEnum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class PatternConstants
 *
 * @author 张麒 2018-3-21.
 * @version Description:
 */
public class PatternConstants {

    public enum Pattern implements BaseEnum<String, String> {

        /**
         * 英文、数字、下划线
         */
        Word("word", "\\\\w+", "英文/数字/下划线"),

        /**
         * 英文/特殊字符
         */
        CommonWord("common_word", "[a-zA-Z0-9_.^%&',;=?$\\\\x22]*$", "英文/特殊字符"),

        /**
         * 双字节字符
         */
        DoubleCharacter("double_character", "[^\\\\x00-\\\\xff]", "双字节字符"),

        /**
         * 数字
         */
        Number("number", "[0-9]*", "数字"),

        /**
         * 整数
         */
        Integer("integer", "^-?[1-9]\\\\d*$", "整数"),

        /**
         * 正整数
         */
        PositiveInteger("positive_integer", "^[1-9]\\\\d*$", "正整数"),

        /**
         * 负整数
         */
        NegativeInteger("negative_integer", "^-[1-9]\\\\d*$", "负整数"),

        /**
         * 正浮点数
         */
        PositiveFloat("positive_float", "^[1-9]\\\\d*\\\\.\\\\d*|0\\\\.\\\\d*[1-9]\\\\d*$", "正浮点数"),

        /**
         * 负浮点数
         */
        NegativeFloat("negative_float", "^-[1-9]\\\\d*\\\\.\\\\d*|-0\\\\.\\\\d*[1-9]\\\\d*$", "负浮点数"),

        /**
         * 不包含中文字符
         */
        NotZH("not_zh", "[^\\\\u4e00-\\\\u9fa5]", "不包含中文字符"),

        /**
         * 中文字符
         */
        ZHWord("zh_word", "[\\\\u4e00-\\\\u9fa5]", "中文字符"),

        /**
         * 中文数字
         */
        ZHNumber("zh_number", "[一二三四五六七九十零]", "中文数字"),

        /**
         * 手机（国内）
         */
        Mobile("mobile", "0?(13|14|15|18)[0-9]{9}", "手机（国内）"),

        /**
         * 电话号码（国内）
         */
        Phone("phone", "\\\\d{3}-\\\\d{8}|\\\\d{4}-\\\\{7,8}", "电话号码（国内）"),

        /**
         * 邮政编码
         */
        ZipCode("zip_code", "\\\\d{3}-\\\\d{8}|\\\\d{4}-\\\\{7,8}", "邮政编码"),

        /**
         * 身份证
         */
        IDCard("id_card", "^(\\\\d{6})(\\\\d{4})(\\\\d{2})(\\\\d{2})(\\\\d{3})([0-9]|X)$", "身份证"),

        /**
         * 日期（yyyy-MM-dd）
         */
        DateDash("date_dash", "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))", "日期（yyyy-MM-dd）"),

        /**
         * Url
         */
        Url("url", "0?(13|14|15|18)[0-9]{9}", "Url"),

        /**
         * Email
         */
        Email("email", "[\\\\w!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[\\\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\\\w](?:[\\\\w-]*[\\\\w])?\\\\.)+[\\\\w](?:[\\\\w-]*[\\\\w])?", "Email");

        Pattern(String key, String pattern, String value) {
            this.key = key;
            this.value = value;
            this.pattern = pattern;
        }

        private String key;
        private String value;
        private String pattern;

        @Override
        public String getCode() {
            return key;
        }

        @Override
        public String getDescription() {
            return value;
        }

        public String getPattern() {
            return pattern;
        }

        public static Pattern getPatternByKey(String key) {
            Pattern value = null;
            for (Pattern pattern : values()) {
                if (key.equals(pattern.getCode())) {
                    value = pattern;
                    break;
                }
            }
            return value;
        }


        private static List<BaseEnum<String, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<BaseEnum<String, String>> getOptions() {
            return options;
        }

        private static Map<String, String> columns = options.stream().collect(Collectors.toMap(BaseEnum::getCode, BaseEnum::getDescription, (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                },
                LinkedHashMap::new));

        public static Map<String, String> getColumns() {
            return columns;
        }
    }

}
