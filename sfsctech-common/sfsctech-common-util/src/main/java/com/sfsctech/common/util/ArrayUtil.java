package com.sfsctech.common.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class ArrayUtil
 *
 * @author 张麒 2016年3月28日
 * @version Description：数组工具类
 */
public class ArrayUtil extends ArrayUtils {

    /**
     * 将字符串数组转换成String
     * <p>
     * <pre>
     * ArrayUtil.toString(new String[] { "Taylor", "Swift" }, ".") = "Taylor.Swift"
     * </pre>
     *
     * @param array    -- 字符串数组
     * @param separate --分隔符
     * @return
     */
    public static String toString(String[] array, String separate) {
        StringBuffer buffer = new StringBuffer();
        if (isNotEmpty(array)) {
            for (String s : array) {
                if (buffer.length() > 0) {
                    buffer.append(separate);
                    buffer.append(s);
                } else {
                    buffer.append(s);
                }
            }
        }
        return buffer.toString();
    }

}
