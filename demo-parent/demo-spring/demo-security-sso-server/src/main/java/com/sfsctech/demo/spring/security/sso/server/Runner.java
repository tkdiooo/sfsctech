package com.sfsctech.demo.spring.security.sso.server;

//import com.sfsctech.core.auth.session.starter.EnableHttpSession;
//import com.sfsctech.core.exception.handler.GlobalExceptionHandler;

import com.sfsctech.core.auth.base.sso.jwt.AccessJwtToken;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
//@SpringBootApplication
//@EnableHttpSession
//@Import({WebConfig.class, GlobalExceptionHandler.class})
public class Runner {

    public static void main(String[] args) {
        System.out.println(AccessJwtToken.class);
//        SSOConstants.COOKIE_ACCESS_TOKEN
//        SpringApplication.run(Runner.class, args);
    }



}
