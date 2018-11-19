package com.sfsctech.core.logger.rmt.kafka.model;

import lombok.Builder;
import lombok.Data;

/**
 * Class TransmitConfig
 *
 * @author 张麒 2018-11-19.
 * @version Description:
 */
@Data
@Builder
public class TransmitConfig {

    private String topic;
    private String brokerList;
}
