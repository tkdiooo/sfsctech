//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.cache;

import java.util.List;
import java.util.Map;

public interface CacheClient {
    String NAMESPACE_SEP = ":";
    String DEFAULT_NAMESPACE = "default";

    void set(String var1, Object var2);

    void set(String var1, Object var2, int var3);

    void set(String var1, String var2, Object var3);

    void set(String var1, String var2, Object var3, int var4);

    void set(Map<String, Object> var1);

    void set(Map<String, Object> var1, int var2);

    void set(String var1, Map<String, Object> var2);

    void set(String var1, Map<String, Object> var2, int var3);

    void incr(String var1);

    void incr(String var1, String var2);

    void decr(String var1);

    void decr(String var1, String var2);

    void del(String var1);

    void del(String var1, String var2);

    void del(List<String> var1);

    void del(String var1, List<String> var2);

    boolean exists(String var1);

    boolean exists(String var1, String var2);

    Map<String, Boolean> exists(List<String> var1);

    Map<String, Boolean> exists(String var1, List<String> var2);

    String get(String var1);

    String get(String var1, String var2);

    <T> T get(String var1, Class<T> var2);

    <T> T get(String var1, String var2, Class<T> var3);

    Map<String, String> get(List<String> var1);

    Map<String, String> get(String var1, List<String> var2);

    <T> Map<String, T> get(List<String> var1, Class<T> var2);

    <T> Map<String, T> get(String var1, List<String> var2, Class<T> var3);
}
