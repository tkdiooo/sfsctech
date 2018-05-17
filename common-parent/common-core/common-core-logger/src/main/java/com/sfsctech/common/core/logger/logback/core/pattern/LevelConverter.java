package com.sfsctech.common.core.logger.logback.core.pattern;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;
import com.sfsctech.common.support.util.StringUtil;

/**
 * Class AppNameConverter
 *
 * @author 张麒 2018-5-17.
 * @version Description:
 */
public class LevelConverter extends BaseConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return super.transform(event, StringUtil.rightPad(event.getLevel().toString(), 5));
    }
}
