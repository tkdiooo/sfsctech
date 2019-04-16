package com.sfsctech.support.eureka;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@EnableEurekaServer
@SpringBootApplication
//@EnableApolloConfig
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
