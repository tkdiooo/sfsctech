package com.sfsctech.website.jsp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.framework.inf.DubboxService;
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

    @Reference(version = "1.0.0")
    private DubboxService service;

    @GetMapping("index.html")
    public String index() {
        RpcResult result = service.DubboxTestMethod("adasd", "sdadad");
        System.out.println(result);
        System.out.println("IndexController.index()");
        return "index";
    }
}
