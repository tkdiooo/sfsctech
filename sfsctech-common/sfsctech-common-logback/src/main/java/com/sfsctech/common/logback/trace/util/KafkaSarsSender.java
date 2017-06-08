package com.sfsctech.common.logback.trace.util;

import com.alibaba.fastjson.JSONObject;
import kafka.utils.VerifiableProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Cluster;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * Class KafkaSarsSender
 *
 * @author 张麒 2017/6/8.
 * @version Description:
 */
public class KafkaSarsSender implements Partitioner {

    private static final Random random = new Random();
    private Producer<String, String> producer = null;
    private String localHost = null;
    private String zfcode = null;
    private String fileName = null;

    // Partitioner
    public KafkaSarsSender(VerifiableProperties props) {

    }

    public KafkaSarsSender(String brokerList, String zfcode, String fileName) {
        this.zfcode = zfcode;
        this.fileName = fileName;
        List<String> localHosts = getLocalIPList();
        localHost = localHosts.isEmpty() ? StringUtils.EMPTY : localHosts.get(0);
        this.producer = initKafkaProducer(brokerList);
    }

    public void push(String text) {
        String ip = "random_key_" + random.nextInt(1024);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("zfcode", zfcode);
        jsonObject.put("log", text);
        jsonObject.put("ip", localHost);
        jsonObject.put("fileName", fileName);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("logback.log", ip, jsonObject.toJSONString());
        producer.send(producerRecord);
    }

    private Producer<String, String> initKafkaProducer(String brokerList) {
        if (StringUtils.isEmpty(brokerList))
            throw new java.lang.IllegalArgumentException("brokerList is Empty!!");
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList.substring(1));//格式：host1:port1,host2:port2,....
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);//a batch size of zero will disable batching entirely
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);//send message without delay
        props.put(ProducerConfig.ACKS_CONFIG, "1");//对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    public int partition(Object arg0, int numPartitions) {
        arg0 = arg0 == null ? "" : arg0;
        int partition = 0;
        partition = Math.abs(arg0.toString().hashCode()) % numPartitions;
        return partition;
    }

    private static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address && !"127.0.0.1".equals(inetAddress.getHostAddress())) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("get local host error >> " + e.getMessage());
        }
        return ipList;
    }

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        System.out.println(111);
        return 0;
    }

    @Override
    public void close() {
        System.out.println(2222);
    }

    @Override
    public void configure(Map<String, ?> map) {
        System.out.println(3333);
    }
}
