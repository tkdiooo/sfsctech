package com.sfsctech.demo.spring.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Class SSOController
 *
 * @author 张麒 2017/7/31.
 * @version Description:
 */
@Controller
public class SSOController {

    private final Logger logger = LoggerFactory.getLogger(SSOController.class);

    @GetMapping("index")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @GetMapping("test")
    public String test(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("test_key"));
        Enumeration<String> enumeration = request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()){
            System.out.println(request.getSession().getAttribute(enumeration.nextElement()));
        }
        return "index";
    }

    @GetMapping("login")
    public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

}
