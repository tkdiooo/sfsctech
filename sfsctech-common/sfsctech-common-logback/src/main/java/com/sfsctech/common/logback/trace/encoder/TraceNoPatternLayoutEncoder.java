package com.sfsctech.common.logback.trace.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import com.sfsctech.common.logback.trace.layout.TraceNoPatternLayout;
import com.sfsctech.common.logback.trace.util.PushUtils;

import java.io.IOException;

/**
 * Class TraceNoPatternLayoutEncoder
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoPatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {

    private boolean synchToMDC = Boolean.FALSE;
    private PushUtils pushUtils = null;
    private String zfcode;
    private String brokerList;
    private String fileName;

    public void start() {
        if (pushUtils == null && synchToMDC) {
            pushUtils = PushUtils.getInstance(brokerList, zfcode, fileName);
        }
        PatternLayout patternLayout = new TraceNoPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

    public byte[] encode(ILoggingEvent event) {
        String txt = layout.doLayout(event);
        if (synchToMDC) {
            pushUtils.push(txt);
        }
        return super.encode(event);
    }

    public void setSynchToMDC(boolean synchToMDC) {
        this.synchToMDC = synchToMDC;
    }

    public void setZfcode(String zfcode) {
        this.zfcode = zfcode;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
