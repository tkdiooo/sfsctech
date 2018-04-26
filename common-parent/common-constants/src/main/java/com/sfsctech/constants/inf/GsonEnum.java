package com.sfsctech.constants.inf;

/**
 * Class GsonEnum
 *
 * @author 张麒 2017-12-18.
 * @version Description:
 */
public interface GsonEnum<E> {

    E deserialize(int code);

}
