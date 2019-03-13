package com.sfsctech.demo.spring.security;

import com.sfsctech.core.auth.session.starter.EnableHttpSession;
import com.sfsctech.core.exception.handler.GlobalExceptionHandler;
import com.sfsctech.core.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@EnableHttpSession
@Import({WebConfig.class, GlobalExceptionHandler.class})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }


}
