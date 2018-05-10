package com.sfsctech.common.core.base.enums;

/**
 * Class BaseEnum
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public interface BaseEnum<K, V> {

    K getCode();

    V getDescription();

    static <K, V> V findValue(BaseEnum<K, V>[] enums, K key) {
        V value = null;
        for (BaseEnum<K, V> e : enums) {
            if (key == e.getCode() || key.equals(e.getCode())) {
                value = e.getDescription();
                break;
            }
        }
        return value;
    }

    static <K, V> K findKey(BaseEnum<K, V>[] enums, V value) {
        K key = null;
        for (BaseEnum<K, V> e : enums) {
            if (e.getDescription().equals(value)) {
                key = e.getCode();
                break;
            }
        }
        return key;
    }
}