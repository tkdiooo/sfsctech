package com.sfsctech.common.util;

import org.apache.commons.lang3.ClassUtils;

/**
 * Class ClassUtil
 *
 * @author 张麒 2016/5/27.
 * @version Description:
 */
public class ClassUtil extends ClassUtils {

    public static Object convert(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof Long) {
            return Long.valueOf(value.toString());
        } else if (value instanceof Integer) {
            return Integer.valueOf(value.toString());
        } else if (value instanceof Double) {
            return Double.valueOf(value.toString());
        } else if (value instanceof Float) {
            return Float.valueOf(value.toString());
        } else if (value instanceof Short) {
            return Short.valueOf(value.toString());
        } else if (value instanceof Boolean) {
            return Boolean.valueOf(value.toString());
        } else {
            return value;
        }
    }

    public static <T> Object convertType(Object value, Class<T> cls) {
        if (value == null) {
            return null;
        } else if (cls == String.class) {
            return value.toString();
        } else if (cls == Long.class) {
            return Long.valueOf(value.toString());
        } else if (cls == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (cls == Double.class) {
            return Double.valueOf(value.toString());
        } else if (cls == Float.class) {
            return Float.valueOf(value.toString());
        } else if (cls == Short.class) {
            return Short.valueOf(value.toString());
        } else if (cls == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else {
            return value;
        }
    }
}
