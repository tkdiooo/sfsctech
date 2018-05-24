package com.sfsctech.core.base.enums;

/**
 * Class StatusEnum
 *
 * @author 张麒 2018-5-24.
 * @version Description:
 */
public interface StatusEnum<K, V> extends BaseEnum<K, V> {

    boolean getSuccessful();
}
