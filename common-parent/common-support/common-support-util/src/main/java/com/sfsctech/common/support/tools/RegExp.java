package com.sfsctech.common.support.tools;


import com.sfsctech.common.core.base.constants.PatternConstants;
import com.sfsctech.common.support.util.RegexpUtil;

import java.util.regex.Matcher;

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
