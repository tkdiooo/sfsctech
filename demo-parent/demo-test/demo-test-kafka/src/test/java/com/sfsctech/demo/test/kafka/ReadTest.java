package com.sfsctech.demo.test.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Class ReadTest
 *
 * @author 张麒 2019-6-25.
 * @version Description:
 */
public class ReadTest {

    private static Logger logger = LoggerFactory.getLogger(KafkaTest.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.16.228.108:9092");//xxx是服务器集群的ip
        properties.put("group.id", "wf_efescocn");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "latest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("wf_efescocn"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                JSONObject jsonAlarmMsg = JSON.parseObject(record.value());
                System.out.println(jsonAlarmMsg);
                System.out.println("-----------------");
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
        }

    }
}
