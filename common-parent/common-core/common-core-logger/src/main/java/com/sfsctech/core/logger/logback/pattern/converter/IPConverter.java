package com.sfsctech.core.logger.logback.pattern.converter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.support.common.util.HttpUtil;

/**
 * Class IPConverter
 *
 * @author 张麒 2018-11-19.
 * @version Description:
 */
public class IPConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return HttpUtil.getServerIp();
    }
}
