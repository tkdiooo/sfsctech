package com.sfsctech.logback.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

/**
 * Class ProducerTest
 *
 * @author 张麒 2017/6/26.
 * @version Description:
 */
public class ProducerTest {

    @Test
    public void producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.225.98:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        int i = 0;
        while (true) {
            ProducerRecord<String, String> r = new ProducerRecord<>("test1", "key-" + i++, "value-" + i);
            System.out.println(r);
            producer.send(r);
        }

//        producer.close();
    }
}
