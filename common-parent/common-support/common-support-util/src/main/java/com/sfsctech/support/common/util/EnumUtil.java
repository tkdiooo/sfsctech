package com.sfsctech.support.common.util;

import com.sfsctech.core.base.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class EnumUtil
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public class EnumUtil {

    private static final Map<Class<? extends BaseEnum>, Map<Object, BaseEnum>> BASE_ENUM_CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends BaseEnum> T getByCode(Object code, Class<? extends T> cls) {
        Map<Object, BaseEnum> enumMap = BASE_ENUM_CACHE.get(cls);
        if (enumMap == null) {
            enumMap = new HashMap<>();
            BaseEnum[] classEnums = cls.getEnumConstants();
            if (classEnums != null && classEnums.length > 0) {
                for (BaseEnum baseEnum : classEnums) {
                    enumMap.put(baseEnum.getCode(), baseEnum);
                }
            }
            enumMap = MapUtil.unmodifiableMap(enumMap);
            BASE_ENUM_CACHE.putIfAbsent(cls, enumMap);
        }
        return (T) enumMap.get(code);
    }
}
