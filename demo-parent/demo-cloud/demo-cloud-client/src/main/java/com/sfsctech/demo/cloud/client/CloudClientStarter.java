package com.sfsctech.demo.cloud.client;

import com.sfsctech.core.spring.config.SpringConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Class Star
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import({SpringConfig.class})
public class CloudClientStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudClientStarter.class).web(true).run(args);
    }
}
