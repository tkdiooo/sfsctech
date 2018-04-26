package com.sfsctech.website.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.framework", "com.sfsctech.website.thymeleaf", "com.sfsctech.configurer"})
public class WebRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebRunner.class, args);
    }
}
