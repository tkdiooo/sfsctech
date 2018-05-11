package com.sfsctech.common.core.logger.logback.rmt.kafka.partitioner;


import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Class CustomPartitioner
 *
 * @author 张麒 2017/6/26.
 * @version Description:
 */
public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] bytes, Object test, byte[] bytes1, Cluster cluster) {
        key = key == null ? "" : key;
        System.out.println("&&:" + Math.abs(key.toString().hashCode()) % cluster.partitionCountForTopic(topic));
        return Math.abs(key.toString().hashCode());
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }


    public int partition(Object arg0, int numPartitions) {
        arg0 = arg0 == null ? "" : arg0;
        int partition = 0;
        partition = Math.abs(arg0.toString().hashCode()) % numPartitions;
        return partition;
    }
}
