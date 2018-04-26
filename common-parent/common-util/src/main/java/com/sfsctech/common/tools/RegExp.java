package com.sfsctech.common.tools;

import com.sfsctech.common.util.RegexpUtil;
import com.sfsctech.constants.PatternConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Validator
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class RegExp {

    public boolean check(PatternConstants.Pattern pattern, String value) {
        Matcher m = RegexpUtil.getPattern(pattern).matcher(value);
        return m.matches();
    }

}
