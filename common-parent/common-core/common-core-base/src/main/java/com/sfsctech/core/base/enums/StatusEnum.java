package com.sfsctech.core.base.enums;

/**
 * Class StatusEnum
 *
 * @author 张麒 2018-5-24.
 * @version Description:
 */
public interface StatusEnum<K, V> extends BaseEnum<K, V> {

    boolean getSuccessful();

    static <K, V> BaseEnum<K, V> getByCode(BaseEnum<K, V>[] enums, K key) {
        BaseEnum<K, V> value = null;
        for (BaseEnum<K, V> e : enums) {
            if (key == e.getCode() || key.equals(e.getCode())) {
                value = e;
                break;
            }
        }
        return value;
    }
}
