package com.sfsctech.common.tools;

import com.sfsctech.common.util.RegexpUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Validator
 *
 * @author 张麒 2016/4/14.
 * @version Description:
 */
public class RegExp {

    public enum Regexp {
        Mobile(RegexpUtil.IS_MOBILE);
        Regexp(Pattern value) {
            this.value = value;
        }
        private Pattern value;
    }

    public boolean check(Regexp regexp, String value) {
        Matcher m = regexp.value.matcher(value);
        return m.matches();
    }

    public boolean isMobileNO(String mobiles) {
//        Math
        Matcher m = Regexp.Mobile.value.matcher(mobiles);
        return m.matches();
    }
}
