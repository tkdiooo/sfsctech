package com.sfsctech.common.util;

import java.util.regex.Pattern;

/**
 * Class RegexpUtil
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class RegexpUtil {

    private static String ATTR_STR = "\\s+(\\w+)\\s*=\\s*(\"[^\"]*\"|\'[^\']*\'|[^\\s>]+)";

    public static Pattern TAG_NAME = getPattern("<\\s*[/]?(\\w+)\\s*");

    public static Pattern TAG_END = getPattern("<\\s*/+\\w+\\s*>");

    public static Pattern A = getPattern("<a(" + ATTR_STR + ")*\\s*>[^(<\\s*/\\s*a\\s*>)]*<\\s*/\\s*a\\s*>");

    public static Pattern A_START = getPattern("<\\s*a[^>]*>");

    public static Pattern A_END = getPattern("<\\s*/a\\s*>");

    public static Pattern HREF_URL = getPattern("href\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>])");

    public static Pattern ATTR = getPattern(ATTR_STR);

    public static Pattern SRC = getPattern("src\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)");

    public static Pattern SIMPLE_TAG = getPattern("<[^<>]+>");

    public static Pattern HTML_CHAR = getPattern("(&[^;]+;)");

    public static Pattern NON_TAG = getPattern(">([^<>]+)<");

    public static Pattern TABLE = getPattern("<\\s*[/]?\\s*table[^>]*>");

    public static Pattern TABLE_START = getPattern("<\\s*\\s*table[^>]*>");

    public static Pattern TABLE_END = getPattern("<\\s*[/]+\\s*table\\s*>");

    public static Pattern TR = getPattern("<\\s*[/]?\\s*tr[^>]*>");

    public static Pattern TR_START = getPattern("<\\s*tr[^>]*>");

    public static Pattern TR_END = getPattern("<\\s*[/]+\\s*tr[^>]*>");

    public static Pattern TD = getPattern("<\\s*[/]?\\s*td[^>]*>");

    public static Pattern TD_START = getPattern("<\\s*td[^>]*>");

    public static Pattern TD_END = getPattern("<\\s*[/]+\\s*td[^>]*>");

    public static Pattern DIV_START = getPattern("<\\s*div[^>]*>");

    public static Pattern DIV_END = getPattern("<\\s*[/]+\\s*div[^>]*>");

    public static Pattern WORD = getPattern("\\w+");

    public static Pattern VALUE = getPattern("value\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)");

    public static Pattern NUMBER_ZH = getPattern("[一二三四五六七九十零]");

    public static Pattern IS_NUMBER = getPattern("[0-9]*");

    public static Pattern IS_MOBILE = getPattern("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$");

    public static Pattern IS_EMAIL = getPattern("\\\\w+@(\\\\w+.)+[a-z]{2,3}");

    /**
     * @param patternString 正则验证
     * @return
     */
    private static Pattern getPattern(String patternString) {
        return Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    }
}
