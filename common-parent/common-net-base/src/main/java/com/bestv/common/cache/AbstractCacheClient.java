//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.cache;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections.MapUtils;

public abstract class AbstractCacheClient implements CacheClient {
    public AbstractCacheClient() {
    }

    public final void set(String key, Object value) {
        this.set("default", key, value);
    }

    public final void set(Map<String, Object> cacheMap) {
        this.set("default", cacheMap);
    }

    public final void set(String key, Object value, int expire) {
        this.set("default", key, value, expire);
    }

    public final void set(Map<String, Object> cacheMap, int expire) {
        this.set("default", cacheMap, expire);
    }

    public final void incr(String key) {
        this.incr("default", key);
    }

    public final void decr(String key) {
        this.decr("default", key);
    }

    public final void del(String key) {
        this.del("default", key);
    }

    public final void del(List<String> keys) {
        this.del("default", keys);
    }

    public final boolean exists(String key) {
        return this.exists("default", key);
    }

    public final Map<String, Boolean> exists(List<String> keys) {
        return this.exists("default", keys);
    }

    public final String get(String key) {
        return this.get((String)"default", (String)key);
    }

    public final <T> T get(String namespace, String key, Class<T> clazz) {
        String cacheString = this.get((String)namespace, (String)key);
        return JSON.parseObject(cacheString, clazz);
    }

    public final <T> T get(String key, Class<T> clazz) {
        return this.get("default", key, clazz);
    }

    public final Map<String, String> get(List<String> keys) {
        return this.get((String)"default", (List)keys);
    }

    public final <T> Map<String, T> get(String namespace, List<String> keys, Class<T> clazz) {
        Map<String, String> stringMap = this.get((String)namespace, (List)keys);
        if (MapUtils.isEmpty(stringMap)) {
            return null;
        } else {
            Map<String, T> result = new HashMap();
            Iterator var6 = stringMap.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, String> stringEntry = (Entry)var6.next();
                T cacheObject = JSON.parseObject((String)stringEntry.getValue(), clazz);
                result.put(stringEntry.getKey(), cacheObject);
            }

            return result;
        }
    }

    public final <T> Map<String, T> get(List<String> keys, Class<T> clazz) {
        return this.get("default", keys, clazz);
    }

    protected final String generateKey(String namespace, String key) {
        return namespace + ":" + key;
    }

    protected final String objectToString(Object object) {
        if (object == null) {
            return "";
        } else {
            return object instanceof String ? (String)object : JSON.toJSONString(object);
        }
    }
}
