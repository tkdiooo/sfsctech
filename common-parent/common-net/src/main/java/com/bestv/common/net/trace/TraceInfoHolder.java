//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.trace;

import com.bestv.common.dto.TraceInfo;

public final class TraceInfoHolder {
    private static final ThreadLocal<TraceInfo> TRACE_INFO_LOCAL = new ThreadLocal();

    public TraceInfoHolder() {
    }

    public static void set(TraceInfo traceInfo) {
        TRACE_INFO_LOCAL.set(traceInfo);
    }

    public static TraceInfo get() {
        return (TraceInfo)TRACE_INFO_LOCAL.get();
    }

    public static void clear() {
        TRACE_INFO_LOCAL.remove();
    }
}
