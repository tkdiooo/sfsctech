package com.sfsctech.common.constants;


import com.sfsctech.common.inf.IEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Class DateConstants
 *
 * @author 张麒 2016年3月30日
 * @version Description：
 */
public class DateConstants {

    public enum DateType implements IEnum<Integer, String> {

        SECONDS(7, "秒"),
        MINUTE(6, "分"),
        HOUR(5, "时"),
        DAY(4, "日"),
        WEEK(3, "周"),
        QUARTER(2, "季"),
        MONTH(1, "月"),
        YEAR(0, "年");

        DateType(Integer key, String value) {
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

    public enum Weeks implements IEnum<Integer, String> {

        /**
         * 星期日
         */
        SUNDAY(Calendar.SUNDAY, "星期日"),

        /**
         * 星期一
         */
        MONDAY(Calendar.MONDAY, "星期一"),

        /**
         * 星期二
         */
        TUESDAY(Calendar.TUESDAY, "星期二"),

        /**
         * 星期三
         */
        WEDNESDAY(Calendar.WEDNESDAY, "星期三"),

        /**
         * 星期四
         */
        THURSDAY(Calendar.THURSDAY, "星期四"),

        /**
         * 星期五
         */
        FRIDAY(Calendar.FRIDAY, "星期五"),

        /**
         * 星期六
         */
        SATURDAY(Calendar.SATURDAY, "星期六");

        Weeks(Integer key, String value) {
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

        private static List<IEnum<Integer, String>> options = new ArrayList<>(Arrays.asList(values()));

        public static List<IEnum<Integer, String>> getOptions() {
            return options;
        }
    }

    // -------------------------------------------------------------------------------------------------------------//

    private static final String MS_CN = "毫秒";
    private static final String SEC_CN = "秒";
    private static final String MINUTE_CN = "分";
    private static final String HOUR_CN = "时";
    private static final String DAY_CN = "日";
    private static final String MONTH_CN = "月";
    private static final String YEAR_CN = "年";

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * SSS
     */
    public static final String MS_PATTERN = "SSS";
    /**
     * ss
     */
    public static final String SEC_PATTERN = "ss";
    /**
     * mm
     */
    public static final String MINUTE_PATTERN = "mm";
    /**
     * HH
     */
    public static final String HOUR_PATTERN = "HH";
    /**
     * dd
     */
    public static final String DAY_PATTERN = "dd";
    /**
     * MM
     */
    public static final String MONTH_PATTERN = "MM";
    /**
     * yyyy
     */
    public static final String YEAR_PATTERN = "yyyy";

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * yyyyMMdd
     */
    public static final String PATTERN_YMD = YEAR_PATTERN + MONTH_PATTERN + DAY_PATTERN;
    /**
     * yyyy-MM-dd
     */
    public static final String PATTERN_YMD_DASH = YEAR_PATTERN + LabelConstants.DASH + MONTH_PATTERN + LabelConstants.DASH + DAY_PATTERN;
    /**
     * yyyy/MM/dd
     */
    public static final String PATTERN_YMD_SLASH = YEAR_PATTERN + LabelConstants.FORWARD_SLASH + MONTH_PATTERN + LabelConstants.FORWARD_SLASH
            + DAY_PATTERN;
    /**
     * yyyy年MM月dd日
     */
    public static final String PATTERN_YMD_CN = YEAR_PATTERN + YEAR_CN + MONTH_PATTERN + MONTH_CN + DAY_PATTERN + DAY_CN;

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * HH:mm:ss.SSS
     */
    public static final String PATTERN_HMSM = HOUR_PATTERN + LabelConstants.COLON + MINUTE_PATTERN + LabelConstants.COLON + SEC_PATTERN
            + LabelConstants.PERIOD + MS_PATTERN;
    /**
     * HH时mm分ss秒SSS毫秒
     */
    public static final String PATTERN_HMSM_CN = HOUR_PATTERN + HOUR_CN + MINUTE_PATTERN + MINUTE_CN + SEC_PATTERN + SEC_CN + MS_PATTERN + MS_CN;

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * HH:mm:ss
     */
    public static final String PATTERN_HMS = HOUR_PATTERN + LabelConstants.COLON + MINUTE_PATTERN + LabelConstants.COLON + SEC_PATTERN;
    /**
     * HH时mm分ss秒
     */
    public static final String PATTERN_HMS_CN = HOUR_PATTERN + HOUR_CN + MINUTE_PATTERN + MINUTE_CN + SEC_PATTERN + SEC_CN;

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * HH:mm
     */
    public static final String PATTERN_HM = HOUR_PATTERN + LabelConstants.COLON + MINUTE_PATTERN;
    /**
     * HH时mm分
     */
    public static final String PATTERN_HM_CN = HOUR_PATTERN + HOUR_CN + MINUTE_PATTERN + MINUTE_CN;

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * yyyyMMdd HH:mm:ss
     */
    public static final String PATTERN_DEFAULT = PATTERN_YMD + LabelConstants.SPACE + PATTERN_HMS;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String PATTERN_DEFAULT_DASH = PATTERN_YMD_DASH + LabelConstants.SPACE + PATTERN_HMS;
    /**
     * yyyy/MM/dd HH:mm:ss
     */
    public static final String PATTERN_DEFAULT_SLASH = PATTERN_YMD_SLASH + LabelConstants.SPACE + PATTERN_HMS;
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final String PATTERN_DEFAULT_CN = PATTERN_YMD_CN + LabelConstants.SPACE + PATTERN_HMS_CN;

    // -------------------------------------------------------------------------------------------------------------//

    /**
     * yyyyMMdd HH:mm:ss.SSS
     */
    public static final String PATTERN_FULL = PATTERN_YMD + LabelConstants.SPACE + PATTERN_HMSM;
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String PATTERN_FULL_DASH = PATTERN_YMD_DASH + LabelConstants.SPACE + PATTERN_HMSM;
    /**
     * yyyy/MM/dd HH:mm:ss.SSS
     */
    public static final String PATTERN_FULL_SLASH = PATTERN_YMD_SLASH + LabelConstants.SPACE + PATTERN_HMSM;
    /**
     * yyyy年MM月dd日 HH时mm分ss秒SSS毫秒
     */
    public static final String PATTERN_FULL_CN = PATTERN_YMD_CN + LabelConstants.SPACE + PATTERN_HMSM_CN;
}
