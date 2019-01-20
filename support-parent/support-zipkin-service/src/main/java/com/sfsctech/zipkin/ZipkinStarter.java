package com.sfsctech.zipkin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * Class ConfigService
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@SpringBootApplication
//@EnableZipkinStreamServer
public class ZipkinStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZipkinStarter.class).web(true).run(args);
    }

}
