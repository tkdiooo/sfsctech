package com.sfsctech.resources;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@SpringBootApplication
public class StaticResourcesService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(StaticResourcesService.class).web(true).run(args);
    }
}
