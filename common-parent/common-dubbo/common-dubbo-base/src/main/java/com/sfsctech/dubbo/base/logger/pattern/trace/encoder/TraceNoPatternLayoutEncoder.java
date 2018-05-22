package com.sfsctech.dubbo.base.logger.pattern.trace.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import com.sfsctech.dubbo.base.logger.pattern.trace.layout.TraceNoPatternLayout;

/**
 * 日志异常信息处理
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoPatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {

    @Override
    public void start() {
        PatternLayout patternLayout = new TraceNoPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        return super.encode(event);
    }

}
