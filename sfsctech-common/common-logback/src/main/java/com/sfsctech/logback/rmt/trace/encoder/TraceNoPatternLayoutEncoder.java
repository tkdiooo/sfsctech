package com.sfsctech.logback.rmt.trace.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import com.sfsctech.logback.rmt.trace.layout.TraceNoPatternLayout;
import com.sfsctech.logback.rmt.util.PushUtil;

/**
 * Class TraceNoPatternLayoutEncoder
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class TraceNoPatternLayoutEncoder extends PatternLayoutEncoderBase<ILoggingEvent> {

    private boolean synchToMDC = Boolean.FALSE;
    private PushUtil pushUtil = null;
    private String zfcode;
    private String brokerList;
    private String fileName;
    private String topic;

    public void start() {
        if (pushUtil == null && synchToMDC) {
            pushUtil = PushUtil.getInstance(brokerList, zfcode, fileName, topic);
        }
        PatternLayout patternLayout = new TraceNoPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

    private byte[] convertToBytes(String s) {
        return super.getCharset() == null ? s.getBytes() : s.getBytes(this.getCharset());
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        byte[] txt = this.convertToBytes(this.layout.doLayout(event));
        if (synchToMDC) {
            System.out.println(new String(txt));
            pushUtil.push(new String(txt));
        }
        return txt;
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

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
