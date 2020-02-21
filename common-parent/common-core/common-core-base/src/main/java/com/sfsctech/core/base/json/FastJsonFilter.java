package com.sfsctech.core.base.json;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Class FastJsonFilter
 *
 * @author 张麒 2020-2-20.
 * @version Description:
 */
public class FastJsonFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {
        System.out.println(object);
        return null;
    }
}
