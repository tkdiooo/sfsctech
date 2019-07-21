package com.sfsctech.support.tools.excel.inf;

/**
 * Class ComplexDataMapping
 *
 * @author 张麒 2019/7/21.
 * @version Description:
 */
public interface ComplexDataMapping<T, V> {

    T mapping(V value);
}
