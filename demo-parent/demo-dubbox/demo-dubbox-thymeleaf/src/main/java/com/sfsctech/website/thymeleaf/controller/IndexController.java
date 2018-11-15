package com.sfsctech.website.thymeleaf.controller;

import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.core.security.annotation.Verify;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.website.thymeleaf.model.UserInfo;
import com.sfsctech.website.thymeleaf.rpc.provider.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private AccountService service;

    @Autowired
    private CacheFactory factory;

    @GetMapping("index")
    public String index(ModelMap model) {
        factory.getCacheClient().put("test1", "abc");
//        System.out.println(factory.getCacheClient().get("test1"));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("account");
        userInfo.setPassword("password");
        model.put("userInfo", userInfo);
        SysAccountDto dto = new SysAccountDto();
        dto.setAccount("sdsada11111");
        dto.setPassword("sdsada11111");
        service.save(dto);
        return "index";
    }

    @PostMapping("login")
    @ResponseBody
    public ActionResult<UserInfo> login(ModelMap model, HttpServletRequest request, @Verify UserInfo userInfo, UserInfo test) {
        System.out.println(model);
        System.out.println(request.getParameter("userName"));
        System.out.println(userInfo.getUserName());
        return ActionResult.forSuccess(userInfo);
    }

    @PostMapping("upload")
    @ResponseBody
    public ActionResult upload(@RequestParam(value = "fileUpload") MultipartFile[] files) {
        return ActionResult.forSuccess();
    }

    @GetMapping("test")
    public String test() {
        return "index";
    }
}
