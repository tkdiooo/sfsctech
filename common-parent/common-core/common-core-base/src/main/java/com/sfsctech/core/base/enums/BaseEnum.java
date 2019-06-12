package com.sfsctech.core.base.enums;

import com.sfsctech.core.base.constants.LabelConstants;

/**
 * Class BaseEnum
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public interface BaseEnum<K, V> {

    K getCode();

    V getDescription();

    String toString();

    @SafeVarargs
    static <K, V> V findValue(K key, BaseEnum<K, V>... enums) {
        V value = null;
        for (BaseEnum<K, V> e : enums) {
            if (key == e.getCode() || key.equals(e.getCode())) {
                value = e.getDescription();
                break;
            }
        }
        return value;
    }

    @SafeVarargs
    static <K, V> K findKey(V value, BaseEnum<K, V>... enums) {
        K key = null;
        for (BaseEnum<K, V> e : enums) {
            if (e.getDescription().equals(value)) {
                key = e.getCode();
                break;
            }
        }
        return key;
    }

    @SafeVarargs
    static <K, V> String buildCacheKey(BaseEnum<K, V>... options) {
        StringBuilder sb = new StringBuilder();
        for (BaseEnum<K, V> option : options) {
            if (sb.length() > 0) {
                sb.append(LabelConstants.AMPERSAND);
            }
            sb.append(option.toString());
        }
        return sb.toString();
    }
}