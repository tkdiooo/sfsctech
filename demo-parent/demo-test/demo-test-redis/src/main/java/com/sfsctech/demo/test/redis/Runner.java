package com.sfsctech.demo.test.redis;

import com.sfsctech.core.cache.config.CacheConfig;
//import com.sfsctech.core.exception.controller.GlobalErrorController;
//import com.sfsctech.core.exception.handler.GlobalExceptionHandler;
//import com.sfsctech.core.web.config.WebConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * Class CloudRibbonStarter
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@SpringBootApplication
@Import(CacheConfig.class)
//@Import({WebConfig.class, GlobalErrorController.class, GlobalExceptionHandler.class, CacheConfig.class})
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class).web(WebApplicationType.SERVLET).run(args);
    }
}
