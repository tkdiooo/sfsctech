package com.sfsctech.demo.cloud.ribbon.controller;

import com.sfsctech.demo.cloud.ribbon.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IndexService service;

    @GetMapping("index")
    public String index(@RequestParam String name) {
        return service.index(name);
    }
}
