//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.bestv.common.net.annotation.HideArgument;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class GenericCommonNetLoggerFilter implements PropertyPreFilter {
    private static final Map<Class, Set<String>> CLASS_EXCLUDES_MAP = new ConcurrentHashMap();

    GenericCommonNetLoggerFilter() {
    }

    public boolean apply(JSONSerializer serializer, Object object, String name) {
        Class objectClass = object.getClass();
        Set<String> excludesFieldsNames = (Set)CLASS_EXCLUDES_MAP.get(objectClass);
        if (excludesFieldsNames == null) {
            synchronized(objectClass) {
                if (CLASS_EXCLUDES_MAP.get(objectClass) == null) {
                    this.resolverClassToExcludesMap(objectClass);
                }
            }

            return this.apply(serializer, object, name);
        } else {
            return !excludesFieldsNames.contains(name);
        }
    }

    private void resolverClassToExcludesMap(Class objectClass) {
        Set<String> excludesFieldsNames = new HashSet();
        Field[] fields = objectClass.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (field.isAnnotationPresent(HideArgument.class)) {
                excludesFieldsNames.add(field.getName());
            }
        }

        CLASS_EXCLUDES_MAP.put(objectClass, excludesFieldsNames);
    }
}
