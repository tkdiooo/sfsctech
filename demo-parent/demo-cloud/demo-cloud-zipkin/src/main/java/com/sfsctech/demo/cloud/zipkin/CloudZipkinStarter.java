package com.sfsctech.demo.cloud.zipkin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
//@EnableZipkinServer
public class CloudZipkinStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudZipkinStarter.class).web(WebApplicationType.SERVLET).run(args);
    }
}
