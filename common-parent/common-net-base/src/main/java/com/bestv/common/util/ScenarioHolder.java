//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.lang.enums.CommonScenario;

public class ScenarioHolder {
    private static ThreadLocal<CommonScenario> scenarioThreadLocal = new ThreadLocal();

    private ScenarioHolder() {
    }

    public static <T extends CommonScenario> T get() {
        T scenario = (T) scenarioThreadLocal.get();
        return scenario == null ? (T) CommonScenario.COMMON : scenario;
    }

    public static void set(CommonScenario commonScenario) {
        scenarioThreadLocal.set(commonScenario);
    }

    public static void clear() {
        scenarioThreadLocal.remove();
    }
}
