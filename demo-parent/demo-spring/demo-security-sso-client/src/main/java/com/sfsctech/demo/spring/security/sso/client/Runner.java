package com.sfsctech.demo.spring.security.sso.client;


import com.sfsctech.core.auth.sso.client.config.SSOConfig;
import com.sfsctech.web.starter.WebStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@WebStarter
@Import(SSOConfig.class)
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

}
