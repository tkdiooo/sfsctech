package com.sfsctech.demo.spring.security.sso.client.controller;

import com.sfsctech.core.web.domain.result.ActionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class IndexController
 *
 * @author 张麒 2019-3-15.
 * @version Description:
 */
@RestController
public class IndexController {

    @GetMapping("index")
    public ActionResult<String> index() {
        System.out.println("'111");
        return ActionResult.forSuccess("哦了");
    }
}
