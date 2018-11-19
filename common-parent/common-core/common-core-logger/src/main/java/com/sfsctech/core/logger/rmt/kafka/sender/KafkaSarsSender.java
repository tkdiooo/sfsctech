package com.sfsctech.core.logger.rmt.kafka.sender;

import com.sfsctech.core.base.json.FastJson;
import com.sfsctech.core.logger.rmt.kafka.model.TransmitConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * Class KafkaSarsSender
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class KafkaSarsSender {

    private static final Random random = new Random();
    private Producer<String, String> producer;
    private TransmitConfig config;

    public KafkaSarsSender(TransmitConfig config) {
        this.config = config;
//        List<String> localHosts = NetUtil.getLocalIPList();
//        localHost = localHosts.isEmpty() ? StringUtils.EMPTY : localHosts.get(0);
        this.producer = initKafkaProducer(config.getBrokerList());
    }

    public void push(String text) {
        String ip = "random_key_" + random.nextInt(1024);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("zfcode", zfcode);
//        jsonObject.put("log", text);
//        jsonObject.put("ip", localHost);
//        jsonObject.put("fileName", fileName);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(config.getTopic(), ip, text);
        this.producer.send(producerRecord);
    }

    public void close() {
        this.producer.close();
    }

    private Producer<String, String> initKafkaProducer(String brokerList) {
        if (StringUtils.isEmpty(brokerList))
            throw new IllegalArgumentException("brokerList is Empty!!");
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);//格式：host1:port1,host2:port2,....
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);//a batch size of zero will disable batching entirely
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);//send message without delay
        props.put(ProducerConfig.ACKS_CONFIG, "1");//对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        return new KafkaProducer<>(props);
    }

    @Override
    public String toString() {
        return FastJson.toJSONString(config);
    }
}
