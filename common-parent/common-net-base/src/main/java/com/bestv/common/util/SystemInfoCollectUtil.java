//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import java.lang.reflect.Field;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

public final class SystemInfoCollectUtil {
    private static Sigar sigar = new Sigar();
    private static boolean valid = false;
    private static Exception invalidException = new Exception("没有匹配的系统信息收集器");

    private SystemInfoCollectUtil() {
    }

    public static double getCPUPercent() throws Exception {
        checkEnv();
        CpuPerc cpuPerc = sigar.getCpuPerc();
        return cpuPerc.getCombined() * 100.0D;
    }

    public static double getMemPercent() throws Exception {
        checkEnv();
        Mem mem = sigar.getMem();
        return mem.getUsedPercent();
    }

    private static void checkEnv() throws Exception {
        if (!valid) {
            throw invalidException;
        }
    }

    static {
        try {
            Field open = Sigar.class.getDeclaredField("open");
            open.setAccessible(true);
            if (!(Boolean)open.get(sigar)) {
                throw invalidException;
            }

            valid = true;
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
