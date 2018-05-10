//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sfsctech.common.support.util;

import com.sfsctech.common.core.base.enums.BaseEnum;
import com.sfsctech.common.core.base.ex.ExceptionTips;
import com.sfsctech.common.core.base.ex.GenericException;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

public final class AssertUtil {

    public static void isTrue(boolean value, ExceptionTips<?, String> tips, String... messages) {
        if (!value) {
            String extraMessage = null;
            if (messages.length > 0) {
                if (messages.length == 1 && StringUtil.isNotBlank(messages[0])) {
                    extraMessage = messages[0];
                } else {
                    String messagePattern = messages[0];
                    String[] messageParams = new String[messages.length - 1];
                    System.arraycopy(messages, 1, messageParams, 0, messageParams.length);
                    extraMessage = MessageFormat.format(messagePattern, (Object[]) messageParams);
                }
            }
            throw new GenericException(tips, extraMessage);
        }
    }

    public static void isBlank(String value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(StringUtil.isBlank(value), tips, messages);
    }

    public static void isNotBlank(String value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(StringUtil.isNotBlank(value), tips, messages);
    }

    public static void isEmpty(Collection value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(ListUtil.isEmpty(value), tips, messages);
    }

    public static void isNotEmpty(Collection value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(ListUtil.isNotEmpty(value), tips, messages);
    }

    public static void isNotNull(Object value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(value != null, tips, messages);
    }

    public static void isNull(Object value, ExceptionTips<?, String> tips, String... messages) {
        isTrue(value == null, tips, messages);
    }

    public static void isEnumCodeValid(String enumCode, Class<? extends BaseEnum> enumClass, ExceptionTips<?, String> tips, String... messages) {
        isNotNull(EnumUtil.getByCode(enumCode, enumClass), tips, messages);
    }

    public static void sizeEq(Collection collection, int size, ExceptionTips<?, String> tips, String... messages) {
        isNotNull(collection, tips, "集合对象为null");
        isTrue(collection.size() == size, tips, messages);
    }


    public static void isNotBlank(String str, String message) {
        if (StringUtil.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFile(File file, String message) {
        if (!file.isFile()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String text, String message) {
        if (!StringUtil.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static void hasText(String text, String message) {
        if (!StringUtil.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtil.hasLength(textToSearch) && StringUtil.hasLength(substring) && textToSearch.contains(substring)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtil.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (ListUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (MapUtil.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException((StringUtil.hasLength(message) ? message + " " : "") + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException((StringUtil.hasLength(message) ? message + " " : "") + subType + " is not assignable to " + superType);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }
}
