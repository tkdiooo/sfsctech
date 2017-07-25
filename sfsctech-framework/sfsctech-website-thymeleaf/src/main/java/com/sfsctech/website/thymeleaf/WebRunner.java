package com.sfsctech.website.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.website.thymeleaf", "com.sfsctech.configurer"})
public class WebRunner extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebRunner.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebRunner.class, args);
    }
}
