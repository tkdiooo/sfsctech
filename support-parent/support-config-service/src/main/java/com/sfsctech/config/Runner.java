package com.sfsctech.config;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Class ConfigService
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).web(WebApplicationType.SERVLET).run(args);
    }

}
