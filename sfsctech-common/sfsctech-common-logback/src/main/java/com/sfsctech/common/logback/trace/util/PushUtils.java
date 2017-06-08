package com.sfsctech.common.logback.trace.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class PushUtils
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class PushUtils extends Thread {

    private BlockingQueue<String> logQueue;
    private static PushUtils push;
    private KafkaSarsSender kafka;

    private PushUtils(LinkedBlockingQueue<String> linkedBlockingQueue) {
        logQueue = linkedBlockingQueue;
    }

    public static synchronized PushUtils getInstance(String serverUrl, String zfcode, String fileName) {
        if (push != null)
            return push;
        push = new PushUtils(new LinkedBlockingQueue<String>());
        push.kafka = new KafkaSarsSender(serverUrl, zfcode, fileName);
        push.start();
        return push;
    }

    public void push(String txt) {
        logQueue.offer(txt);
    }

    public void run() {
        while (true) {
            try {
                kafka.push(logQueue.take());
            } catch (Exception e) {
                System.out.println("kafka connect error >> " + e.getMessage());
            }
        }
    }
}
