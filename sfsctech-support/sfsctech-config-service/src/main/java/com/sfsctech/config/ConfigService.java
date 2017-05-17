package com.sfsctech.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Class ConfigService
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigService.class).web(true).run(args);
    }

}
