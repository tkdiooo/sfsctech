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

    K getCode();

    V getContent();

    String toString();

    static <K, V> V findValue(IEnum<K, V>[] enums, K key) {
        V value = null;
        for (IEnum<K, V> e : enums) {
            if (key == e.getCode() || key.equals(e.getCode())) {
                value = e.getContent();
                break;
            }
        }
        return value;
    }

    static <K, V> K findKey(IEnum<K, V>[] enums, V value) {
        K key = null;
        for (IEnum<K, V> e : enums) {
            if (e.getContent().equals(value)) {
                key = e.getCode();
                break;
            }
        }
        return key;
    }
}
