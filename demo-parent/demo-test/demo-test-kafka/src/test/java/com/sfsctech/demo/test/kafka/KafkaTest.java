package com.sfsctech.demo.test.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class RedisTest
 *
 * @author 张麒 2018-8-13.
 * @version Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Runner.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class KafkaTest {

    private static Logger logger = LoggerFactory.getLogger(KafkaTest.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息的方法
     *
     * @param key  推送数据的key
     * @param data 推送数据的data
     */
    private void send(String key, String data) {
        kafkaTemplate.send("test", key, data);
        logger.info("send：key:" + key + "=value:" + data);
    }

    @KafkaListener(topics = "test")
    public void receive(ConsumerRecord<?, ?> consumer) {
        logger.info("Consumer:{} - {}:{}", consumer.topic(), consumer.key(), consumer.value());
    }


    @Test
    public void testKafka() {
        int iMax = 10000;
        for (int i = 1; i < iMax; i++) {
            send("key" + i, "data" + i);
        }
    }
}
