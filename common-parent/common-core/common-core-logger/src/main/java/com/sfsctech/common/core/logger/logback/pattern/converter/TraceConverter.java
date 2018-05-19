package com.sfsctech.common.core.logger.logback.pattern.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.common.core.logger.util.TraceNoUtil;

/**
 * Class TraceNoConverter
 *
 * @author 张麒 2018-5-17.
 * @version Description:
 */
// TODO 次模式需要移植到dubbox模块下
public class TraceConverter extends ClassicConverter {

    public static String appName = "";

    @Override
    public String convert(ILoggingEvent event) {
        return "[" + appName + "," + (TraceNoUtil.getTraceNo() != null ? TraceNoUtil.getTraceNo() : "") + "]";
    }
}
