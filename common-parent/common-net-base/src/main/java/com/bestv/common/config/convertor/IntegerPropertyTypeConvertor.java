//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.config.convertor;

public final class IntegerPropertyTypeConvertor implements PropertyTypeConvertor<Integer> {
    public IntegerPropertyTypeConvertor() {
    }

    public Integer convert(String propertyValueByStr) {
        return propertyValueByStr == null ? null : Integer.valueOf(propertyValueByStr);
    }
}
