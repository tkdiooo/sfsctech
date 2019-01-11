package com.sfsctech.demo.cloud.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Class CloudEurekaStarter
 *
 * @author 张麒 2018-4-27.
 * @version Description:
 */
@EnableEurekaServer
@SpringBootApplication
public class CloudEurekaStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudEurekaStarter.class).web(WebApplicationType.SERVLET).run(args);
    }
}
