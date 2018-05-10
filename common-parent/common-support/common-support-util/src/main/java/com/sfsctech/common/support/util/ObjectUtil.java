package com.sfsctech.common.support.util;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Class ObjectUtil
 *
 * @author 张麒 2017/2/8.
 * @version Description:
 */
public abstract class ObjectUtil extends ObjectUtils {

    public ObjectUtil() {

    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || (obj.getClass().isArray() ? Array.getLength(obj) == 0 : (obj instanceof CharSequence ? ((CharSequence) obj).length() == 0 : (obj instanceof Collection ? ((Collection) obj).isEmpty() : (obj instanceof Map && ((Map) obj).isEmpty()))));
    }

}
