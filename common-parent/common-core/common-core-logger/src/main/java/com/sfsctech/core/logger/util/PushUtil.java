package com.sfsctech.core.logger.util;


import com.sfsctech.core.logger.rmt.kafka.model.TransmitConfig;
import com.sfsctech.core.logger.rmt.kafka.sender.KafkaSarsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class PushUtils
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class PushUtil extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(PushUtil.class);

    private static PushUtil push;

    private BlockingQueue<String> logQueue;
    private KafkaSarsSender kafka;
    private boolean isRun = true;

    private PushUtil(LinkedBlockingQueue<String> linkedBlockingQueue) {
        logQueue = linkedBlockingQueue;
    }

    public static synchronized PushUtil getInstance(TransmitConfig config) {
        if (push != null){
            push.isRun = true;
            return push;
        }
        push = new PushUtil(new LinkedBlockingQueue<>());
        push.kafka = new KafkaSarsSender(config);
        push.start();
        logger.info("PushUtil initialize succeed[" + push.kafka.toString() + "]");
        return push;
    }

    public void push(String txt) {
        logQueue.offer(txt);
    }

    public void close() {
        this.isRun = false;
        logger.info("PushUtil Ready to stop");
    }

    public void run() {
        System.out.println(isRun);
        while (isRun) {
            try {
                kafka.push(logQueue.take());
            } catch (Exception e) {
                logger.error("kafka connect error >> " + e.getMessage());
            }
        }
        kafka.close();
        logger.info("PushUtil Stop success");
    }
}
