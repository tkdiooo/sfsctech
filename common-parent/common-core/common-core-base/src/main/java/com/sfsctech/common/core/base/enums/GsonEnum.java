package com.sfsctech.common.core.base.enums;

/**
 * Class GsonEnum
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public interface GsonEnum<T> {

    T deserialize(int code);

}
