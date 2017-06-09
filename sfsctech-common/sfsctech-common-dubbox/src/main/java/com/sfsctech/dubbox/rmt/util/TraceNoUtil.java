package com.sfsctech.dubbox.rmt.util;

import com.alibaba.dubbo.rpc.proxy.TraceUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class TraceNoUtil
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
public class TraceNoUtil {

    /**
     * 产生新的traceNo,并放到MDC中
     */
    public static void newTraceNo() {
        TraceUtil.setTraceNO(RandomStringUtils.randomAlphanumeric(12));
    }

    /**
     * 产生新的traceNo,并放到MDC中<br>
     * parentTranceNo-newTraceNo
     */
    public static void newTraceNo(String parentTranceNo) {
        if (StringUtils.isNotEmpty(parentTranceNo)) {
            TraceUtil.setTraceNO(parentTranceNo + "-" + RandomStringUtils.randomAlphanumeric(6));
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
        return TraceUtil.getTraceNo();
    }

    /**
     * 清除MDC中的traceNo
     */
    public static void clearTraceNo() {
        TraceUtil.clearTraceNo();
    }
}
