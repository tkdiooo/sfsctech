package com.sfsctech.website.thymeleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class IndexController
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("index")
    public String index() {
        return "index";
    }
}
