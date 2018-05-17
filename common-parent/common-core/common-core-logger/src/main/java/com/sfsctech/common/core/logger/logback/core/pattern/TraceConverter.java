package com.sfsctech.common.core.logger.logback.core.pattern;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.common.core.logger.logback.rmt.util.TraceNoUtil;

/**
 * Class TraceNoConverter
 *
 * @author 张麒 2018-5-17.
 * @version Description:
 */
public class TraceConverter extends BaseConverter {

    public static String appName = "";

    @Override
    public String convert(ILoggingEvent event) {
        return super.transform(event, "[" + appName + "," + (TraceNoUtil.getTraceNo() != null ? TraceNoUtil.getTraceNo() : "") + "]");
    }
}
