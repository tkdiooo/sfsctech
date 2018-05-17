package com.sfsctech.demo.cloud.feign;

import com.sfsctech.common.cloud.net.annotation.EnableCloudServiceClient;
import com.sfsctech.common.core.logger.config.LogbackConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * Class Star
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCloudServiceClient(basePackages = {"com.sfsctech.demo.cloud.inf.service"})
@Import(LogbackConfig.class)
public class CloudFeignStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudFeignStarter.class).web(true).run(args);
    }
}
