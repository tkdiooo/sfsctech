package com.sfsctech.website.thymeleaf;

import com.sfsctech.dubbo.starter.annotation.EnableDubboConsumeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableDubboConsumeService
public class WebRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebRunner.class, args);
    }
}
