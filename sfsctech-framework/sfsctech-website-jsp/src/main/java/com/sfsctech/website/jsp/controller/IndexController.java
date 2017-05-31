package com.sfsctech.website.jsp.controller;

import com.sfsctech.common.base.model.PagingInfo;
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
        PagingInfo<SysAccountDto> pagingInfo = new PagingInfo<>();
        pagingInfo.setStart(1);
        pagingInfo = accountService.findByPage(pagingInfo);
        System.out.println(pagingInfo);
        pagingInfo.getData().forEach(System.out::println);
        System.out.println("IndexController.index()");
        return "index";
    }
}
