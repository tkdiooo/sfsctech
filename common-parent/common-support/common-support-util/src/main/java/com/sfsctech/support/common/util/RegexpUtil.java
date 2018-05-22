package com.sfsctech.support.common.util;

import com.sfsctech.core.base.constants.PatternConstants;

import java.util.regex.Pattern;

/**
 * Class RegexpUtil
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class RegexpUtil {

    /**
     * @param patternString 正则验证
     * @return java.util.regex.Pattern
     */
    private static Pattern getPattern(String patternString) {
        return Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    }

    /**
     * 获取正则对象
     *
     * @param pattern PatternConstants.Pattern
     * @return java.util.regex.Pattern
     */
    public static Pattern getPattern(PatternConstants.Pattern pattern) {
        return getPattern(pattern.getPattern());
    }
}
