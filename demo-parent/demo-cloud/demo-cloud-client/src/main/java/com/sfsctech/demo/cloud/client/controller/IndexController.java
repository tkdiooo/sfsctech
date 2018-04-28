package com.sfsctech.demo.cloud.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class IndexController
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@RestController
public class IndexController {

    @Value("${server.port}")
    String port;

    @GetMapping("index")
    public String home(@RequestParam String name) {
        return "hi " + name + ",i am from port:" + port;
    }

}
