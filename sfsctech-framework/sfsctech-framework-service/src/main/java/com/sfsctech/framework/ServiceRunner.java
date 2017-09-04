package com.sfsctech.framework;

import com.sfsctech.dubbox.properties.DubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class ServiceRunning
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.framework", "com.sfsctech.configurer"})
public class ServiceRunner {

    public static void main(String[] args) throws Exception {
        DubboConfig.setServicePackage("com.sfsctech.framework.rpc");
        SpringApplication.run(ServiceRunner.class, args);
    }
}
