/**
 *
 */
package com.sfsctech.constants.inf;

/**
 * Class EnumSpecification
 *
 * @author 张麒 2016年3月27日
 * @version Description：
 */
public interface IEnum<K, V> {

    K getKey();

    V getValue();

    static <K, V> V findValue(IEnum<K, V>[] enums, K key) {
        V value = null;
        for (IEnum<K, V> e : enums) {
            if (key == e.getKey() || key.equals(e.getKey())) {
                value = e.getValue();
                break;
            }
        }
        return value;
    }

    static <K, V> K findKey(IEnum<K, V>[] enums, V value) {
        K key = null;
        for (IEnum<K, V> e : enums) {
            if (e.getValue().equals(value)) {
                key = e.getKey();
                break;
            }
        }
        return key;
    }
}
