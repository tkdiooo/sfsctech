package com.sfsctech.website.jsp.controller;

import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.website.jsp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Class IndexController
 *
 * @author 张麒 2017/5/12.
 * @version Description:
 */
@Controller
public class IndexController {

    @Autowired
    private AccountService accountService;

    @GetMapping("index.html")
    public String index() {
        List<SysAccountDto> list = accountService.findByPage(1, 10);
        list.forEach(System.out::println);
        System.out.println("IndexController.index()");
        return "index";
    }
}
