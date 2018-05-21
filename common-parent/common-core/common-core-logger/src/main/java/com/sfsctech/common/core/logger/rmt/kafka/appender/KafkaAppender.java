package com.sfsctech.common.core.logger.rmt.kafka.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.status.ErrorStatus;
import com.sfsctech.common.core.logger.util.PushUtil;

/**
 * Class KafkaAppender
 *
 * @author 张麒 2018-5-21.
 * @version Description:
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private Encoder<ILoggingEvent> encoder;

    private PushUtil pushUtil = null;
    private String topic;
    private String zfcode;
    private String fileName;
    private String brokerList;

    @Override
    public void start() {
        int errors = 0;
        if (this.encoder == null) {
            this.addStatus(new ErrorStatus("No encoder set for the appender named \"" + this.name + "\".", this));
            ++errors;
        }

        if (errors == 0) {
            super.start();
            if (pushUtil == null) {
                pushUtil = PushUtil.getInstance(brokerList, zfcode, fileName, topic);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        pushUtil.close();
    }

    @Override
    protected void append(ILoggingEvent event) {
        byte[] msg = this.encoder.encode(event);
        pushUtil.push(new String(msg));
    }

    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public void setZfcode(String zfcode) {
        this.zfcode = zfcode;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
