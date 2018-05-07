//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.dto.EnvContext;

public class EnvironmentHolder {
    private static final ThreadLocal<EnvContext> FLAME_ID_THREAD_HOLDER = new ThreadLocal();

    public EnvironmentHolder() {
    }

    public static void set(EnvContext envContext) {
        FLAME_ID_THREAD_HOLDER.set(envContext);
    }

    public static EnvContext get() {
        return (EnvContext)FLAME_ID_THREAD_HOLDER.get();
    }
}
