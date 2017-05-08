package com.sfsctech.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class StringUtil
 *
 * @author 张麒 2016年3月27日
 * @version Description：
 */
public class StringUtil extends StringUtils {

    /**
     * 替换字符串中的空白字符
     * <pre>
     * StringUtil.clearSpace("A B	C 　D\r\nE ") = "A B C D E"
     * </pre>
     *
     * @param target      "A B	C 　D\r\nE "
     * @param replacement 替换符，默认空格
     * @return "A B C D E"
     */
    public static String clearSpace(String target, String replacement) {
        if (StringUtils.isBlank(target)) return "";
        if (StringUtils.isBlank(replacement)) replacement = " ";
        return target.replaceAll("　", replacement).replaceAll("\\s+", replacement).trim();
    }

    /**
     * 清除特殊字符
     * <pre>
     * StringUtil.clearSpecialChar("!qaz@wsx#edc$rfv") = "qazwsxedcrfv"
     * </pre>
     *
     * @param target !qaz@wsx#edc$rfv
     * @return qazwsxedcrfv
     */
    public static String clearSpecialChar(String target) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(target);
        return m.replaceAll("").trim();
    }

    /**
     * 判断字符串是否是数字
     * <pre>
     * StringUtil.isNumeric(null) = false
     * StringUtil.isNumeric("") = false
     * StringUtil.isNumeric("  ") = false
     * StringUtil.isNumeric("123") = true
     * StringUtil.isNumeric("12 3") = false
     * StringUtil.isNumeric("ab2c") = false
     * StringUtil.isNumeric("12-3") = false
     * StringUtil.isNumeric("12.3") = true
     * StringUtil.isNumeric("-123") = true
     * StringUtil.isNumeric("+123") = true
     * </pre>
     */
    public static boolean isNumeric(String target) {
        return StringUtils.isNotBlank(target) && target.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$") && (target.length() <= 1 || target.charAt(0) != '0');
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtils.isBlank("null")    = true
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs) || "null".equals(cs);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <pre>
     * StringUtils.isNotBlank("null")    = false
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 字符串index位字符小写
     * <pre>
     * StringUtil.toLowerCaseByIndex(null, 1) = ""
     * StringUtil.toLowerCaseByIndex("", 1) = ""
     * StringUtil.toLowerCaseByIndex("ABC", 0) = "ABC"
     * StringUtil.toLowerCaseByIndex("ABC", 1) = "aBC"
     * StringUtil.toLowerCaseByIndex("ABC", 2) = "AbC"
     * StringUtil.toLowerCaseByIndex("ABC", 3) = "ABc"
     * StringUtil.toLowerCaseByIndex("ABC", 4) = "ABC"
     * </pre>
     */
    public static String toLowerCaseByIndex(String target, int index) {
        if (isNotBlank(target)) {
            if (index == 0)
                return target;
            if (index > target.length())
                return target;
            else
                return target.substring(0, index - 1) + target.substring(index - 1, index).toLowerCase() + target.substring(index);
        }
        return "";
    }

    public static boolean hasLength(CharSequence input) {
        return input != null && input.length() > 0;
    }

    public static boolean hasLength(String input) {
        return hasLength((CharSequence) input);
    }

    public static boolean hasText(CharSequence input) {
        if (!hasLength(input)) {
            return false;
        } else {
            int strLen = input.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(input.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String input) {
        return hasText((CharSequence) input);
    }

    /**
     * 英文字符转换ASCII数字
     *
     * @param input
     * @return
     */
    public static String convertNumber(String input) {
        if (StringUtil.isBlank(input)) {
            return input;
        }

        StringBuilder buffer = new StringBuilder();
        char[] chars = input.toUpperCase().toCharArray();
        for (char c : chars) {
            if (c > 64 && c < 91) {
                buffer.append((c - 64));
            }
        }
        return buffer.toString();
    }
}