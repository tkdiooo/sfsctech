package com.sfsctech.resources.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Class IndexController
 *
 * @author 张麒 2018-6-4.
 * @version Description:
 */
@RestController
public class IndexController {

    @PostMapping("index")
    public String index(HttpServletRequest request) {
        System.out.println(request.getParameter("userName"));
        return "index";
    }
}
