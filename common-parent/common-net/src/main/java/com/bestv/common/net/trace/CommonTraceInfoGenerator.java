//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.trace;

import com.bestv.common.dto.TraceInfo;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;

public class CommonTraceInfoGenerator implements TraceInfoGenerator {
    private static final Map<String, String> TRACE_CURRENT_SPAN_ID_MAP = new ConcurrentHashMap();
    private static final String SPAN_ID_SEP = ".";
    private static final String START_IN_THIS_APP = ".0";
    private static final String START_SPAN_ID = "1.0";

    public CommonTraceInfoGenerator() {
    }

    public void load(TraceInfo traceInfo) {
        if (TRACE_CURRENT_SPAN_ID_MAP.get(traceInfo.getTraceId()) != null) {
            throw new RuntimeException("该traceId已经载入过, 不可再次载入. traceId: " + traceInfo.getTraceId());
        } else {
            TRACE_CURRENT_SPAN_ID_MAP.put(traceInfo.getTraceId(), traceInfo.getSpanId() + ".0");
        }
    }

    public TraceInfo generate(TraceInfo traceInfo) {
        String currentSpanId = (String)TRACE_CURRENT_SPAN_ID_MAP.get(traceInfo.getTraceId());
        if (StringUtils.isBlank(currentSpanId)) {
            currentSpanId = "1.0";
        } else {
            String currentLastPartSpanId = currentSpanId.substring(currentSpanId.lastIndexOf(".") + 1);
            currentLastPartSpanId = String.valueOf(Integer.valueOf(currentLastPartSpanId) + 1);
            currentSpanId = currentSpanId.substring(0, currentSpanId.lastIndexOf(".") + 1) + currentLastPartSpanId;
        }

        TraceInfo generatedTraceInfo = new TraceInfo();
        generatedTraceInfo.setTraceId(traceInfo.getTraceId());
        generatedTraceInfo.setSpanId(currentSpanId);
        TRACE_CURRENT_SPAN_ID_MAP.put(generatedTraceInfo.getTraceId(), generatedTraceInfo.getSpanId());
        return generatedTraceInfo;
    }

    public TraceInfo generate() {
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setTraceId(UUID.randomUUID().toString());
        traceInfo.setSpanId("1.0");
        this.load(traceInfo);
        return traceInfo;
    }

    public void unload(String traceId) {
        TRACE_CURRENT_SPAN_ID_MAP.remove(traceId);
    }
}
