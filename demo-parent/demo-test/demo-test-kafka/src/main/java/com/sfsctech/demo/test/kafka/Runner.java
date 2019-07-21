package com.sfsctech.demo.test.kafka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).run(args);
    }
}
