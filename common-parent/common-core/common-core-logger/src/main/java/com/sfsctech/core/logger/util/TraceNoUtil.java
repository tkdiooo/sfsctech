package com.sfsctech.core.logger.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * Class TraceNoUtils
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoUtil {

    public static final String TRACE_ID = "X-B3-TraceId";

    /**
     * 产生新的traceNo,并放到MDC中
     */
    public static void newTraceNo() {
        MDC.put(TRACE_ID, RandomStringUtils.randomAlphanumeric(12));
    }

    /**
     * 产生新的traceNo,并放到MDC中<br>
     * parentTranceNo-newTraceNo
     */
    public static void newTraceNo(String parentTranceNo) {
        if (StringUtils.isNotEmpty(parentTranceNo)) {
            MDC.put(TRACE_ID, parentTranceNo + "-" + RandomStringUtils.randomAlphanumeric(6));
        } else {
            newTraceNo();
        }
    }

    /**
     * 获得MDC中的traceNo
     *
     * @return
     */
    public static String getTraceNo() {
        return MDC.get(TRACE_ID);
    }

    /**
     * 清除MDC中的traceNo
     */
    public static void clearTraceNo() {
        MDC.remove(TRACE_ID);
    }
}
