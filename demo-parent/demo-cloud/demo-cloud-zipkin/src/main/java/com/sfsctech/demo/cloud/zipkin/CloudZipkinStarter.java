package com.sfsctech.demo.cloud.zipkin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import zipkin.server.EnableZipkinServer;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
@EnableZipkinServer
public class CloudZipkinStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudZipkinStarter.class).web(true).run(args);
    }
}
