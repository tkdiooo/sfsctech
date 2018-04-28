package com.sfsctech.demo.cloud.turbine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableTurbine
public class CloudTurbineStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudTurbineStarter.class).web(true).run(args);
    }
}
