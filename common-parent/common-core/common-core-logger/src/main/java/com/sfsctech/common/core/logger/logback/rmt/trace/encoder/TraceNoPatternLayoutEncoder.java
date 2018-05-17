package com.sfsctech.common.core.logger.logback.rmt.trace.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import com.sfsctech.common.core.logger.logback.core.pattern.TraceConverter;
import com.sfsctech.common.core.logger.logback.rmt.trace.layout.TraceNoPatternLayout;
import com.sfsctech.common.core.logger.logback.rmt.util.PushUtil;
import com.sfsctech.common.support.util.StringUtil;

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
        if (StringUtil.isNotBlank(zfcode)) {
            TraceConverter.appName = zfcode;
        }
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

    @Override
    public byte[] encode(ILoggingEvent event) {
        byte[] txt = super.encode(event);
        if (synchToMDC) {
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
