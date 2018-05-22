package com.sfsctech.dubbo.base.logger.pattern.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.dubbo.base.logger.pattern.trace.util.TraceNoUtil;

/**
 * Class TraceNoConverter
 *
 * @author 张麒 2018-5-17.
 * @version Description:
 */
public class TraceConverter extends ClassicConverter {

    public static String appName = "";

    @Override
    public String convert(ILoggingEvent event) {
        return "[" + appName + "," + (TraceNoUtil.getTraceNo() != null ? TraceNoUtil.getTraceNo() : "") + "]";
    }
}
