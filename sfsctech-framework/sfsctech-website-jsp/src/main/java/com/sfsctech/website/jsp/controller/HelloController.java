package com.sfsctech.website.jsp.controller;

import com.sfsctech.common.spring.properties.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

/**
 * Class HelloController
 *
 * @author 张麒 2017/5/4.
 * @version Description:
 */

@Controller
public class HelloController {

    // 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    @Value("${application.hello:Hello Angel}")
    private String hello;


    @GetMapping("/helloJsp.html")
    public String helloJsp(Map<String, Object> model) {
        System.out.println("HelloController.helloJsp().hello=" + hello);
        model.put("time", new Date());
        model.put("message", this.hello);
        return "welcome";
    }
}
