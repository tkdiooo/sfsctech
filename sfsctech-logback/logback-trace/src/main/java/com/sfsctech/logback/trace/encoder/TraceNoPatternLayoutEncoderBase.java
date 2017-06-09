package com.sfsctech.logback.trace.encoder;

import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import com.sfsctech.logback.trace.util.PushUtil;

/**
 * Class TraceNoPatternLayoutEncoderBase
 *
 * @author 张麒 2017/6/9.
 * @version Description:
 */
public abstract class TraceNoPatternLayoutEncoderBase<ILoggingEvent> extends PatternLayoutEncoderBase<ILoggingEvent> {

    protected boolean synchToMDC = Boolean.FALSE;
    protected PushUtil pushUtil = null;
    protected String zfcode;
    protected String brokerList;
    protected String fileName;

    protected byte[] convertToBytes(String s) {
        return super.getCharset() == null ? s.getBytes() : s.getBytes(this.getCharset());
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        getPattern();
        System.out.println(this.layout.doLayout(event));
        System.out.println(new String(super.encode(event)));
        byte[] txt = this.convertToBytes(this.layout.doLayout(event));
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
}
