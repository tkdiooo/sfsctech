package com.sfsctech.website.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class IndexController
 *
 * @author 张麒 2017/5/12.
 * @version Description:
 */
@Controller
public class IndexController {

    @GetMapping("index.html")
    public String index() {
        return "index";
    }
}
