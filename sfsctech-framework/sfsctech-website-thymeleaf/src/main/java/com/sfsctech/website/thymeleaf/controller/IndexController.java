package com.sfsctech.website.thymeleaf.controller;

import com.sfsctech.website.thymeleaf.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("login")
    public void login(ModelMap model, HttpServletRequest request, UserInfo userInfo) {
        System.out.println(model);
        System.out.println(request.getParameter("userName"));
        System.out.println(userInfo.getUserName());
    }
}
