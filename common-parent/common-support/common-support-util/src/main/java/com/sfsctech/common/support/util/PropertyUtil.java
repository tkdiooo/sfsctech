package com.sfsctech.common.support.util;

import java.util.Properties;

/**
 * Class PropertyPlaceholderUtil
 *
 * @author 张麒 2016/5/17.
 * @version Description:
 */
public class PropertyUtil {

    private static Properties allprops = new Properties();

    public static String getProps(String key) {
        return allprops.getProperty(key);
    }

    public static Properties getProps() {
        return allprops;
    }

    public static void setProps(Properties props) {
        for (Object key : props.keySet()) {
            allprops.put(key, props.getProperty(key.toString()));
        }
    }
}
