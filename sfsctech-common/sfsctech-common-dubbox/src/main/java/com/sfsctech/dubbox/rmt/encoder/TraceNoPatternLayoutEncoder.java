package com.sfsctech.dubbox.rmt.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sfsctech.dubbox.rmt.encoder.layout.DubboxTraceNoPatternLayout;
import com.sfsctech.logback.trace.encoder.TraceNoPatternLayoutEncoderBase;
import com.sfsctech.logback.trace.util.PushUtil;

/**
 * Class TraceNoPatternLayoutEncoder
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
public class TraceNoPatternLayoutEncoder extends TraceNoPatternLayoutEncoderBase<ILoggingEvent> {

    public void start() {
        if (pushUtil == null && synchToMDC) {
            pushUtil = PushUtil.getInstance(brokerList, zfcode, fileName);
        }
        PatternLayout patternLayout = new DubboxTraceNoPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }
}
