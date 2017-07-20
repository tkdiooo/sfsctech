package com.sfsctech.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class FilterConstants
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class FilterConstants {

    // Filter Attribute
    //-------------------------------------------------------------------------------------------
    public static final String FILTER_EXCLUDES_KEY = "exclusions";

    public static Set<String> FILTER_EXCLUDES_VALUE;

    static {
        FILTER_EXCLUDES_VALUE = new HashSet<>();
        FILTER_EXCLUDES_VALUE.add("/druid/*");
    }


    public static void addFilterExcludes(String... excludes) {
        FILTER_EXCLUDES_VALUE.addAll(Arrays.asList(excludes));
    }

    public static String getaddFilterExcludes() {
        return toString(FILTER_EXCLUDES_VALUE, LabelConstants.COMMA);
    }

    private static String toString(Set<String> set, String separate) {
        StringBuilder buffer = new StringBuilder();
        set.forEach(s -> {
            if (buffer.length() > 0) {
                buffer.append(separate);
                buffer.append(s);
            } else {
                buffer.append(s);
            }
        });
        return buffer.toString();
    }
}
