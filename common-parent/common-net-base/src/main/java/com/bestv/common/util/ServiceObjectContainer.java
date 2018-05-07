//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import java.util.HashMap;

public class ServiceObjectContainer {
    private static ThreadLocal<HashMap<String, Object>> threadLocal = new ThreadLocal();

    public ServiceObjectContainer() {
    }

    public static void put(String key, Object value) {
        init();
        threadLocal.get().put(key, value);
    }

    public static <T> T get(String key) {
        init();
        return (T) threadLocal.get().get(key);
    }

    public static void clear() {
        init();
        threadLocal.get().clear();
    }

    private static void init() {
        if (threadLocal.get() == null) {
            threadLocal.set(new HashMap());
        }

    }
}
