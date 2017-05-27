package com.sfsctech.framework;

import com.sfsctech.common.dubbox.properties.DubboConfig;
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
@ComponentScan(basePackages = {"com.sfsctech.framework", "com.sfsctech.common"})
public class ServiceRunner {

    public static void main(String[] args) throws Exception {
        DubboConfig.setAnnotationPackage("com.sfsctech.framework.rpc.provider");
        SpringApplication.run(ServiceRunner.class, args);
    }
}
