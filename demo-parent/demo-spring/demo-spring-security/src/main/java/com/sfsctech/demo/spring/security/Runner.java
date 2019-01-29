package com.sfsctech.demo.spring.security;

import com.sfsctech.core.auth.session.config.SessionConfig;
import com.sfsctech.core.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@SpringBootApplication
@EnableRedisHttpSession
@Import({WebConfig.class, SessionConfig.class})
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

}
