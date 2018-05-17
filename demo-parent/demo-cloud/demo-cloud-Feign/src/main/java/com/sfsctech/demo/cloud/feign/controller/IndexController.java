package com.sfsctech.demo.cloud.feign.controller;

import com.sfsctech.common.core.rpc.result.ActionResult;
import com.sfsctech.demo.cloud.feign.service.IndexService;
import com.sfsctech.demo.cloud.inf.request.CheckBindingReq;
import com.sfsctech.demo.cloud.inf.request.CheckBindingRes;
import com.sfsctech.demo.cloud.inf.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class IndexController
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService service;

    @Autowired
    private ClientService clientService;

    @GetMapping("index")
    public String index(@RequestParam String name) {
        CheckBindingReq cbReq = new CheckBindingReq();
        cbReq.setBuCode(name);
        ActionResult<CheckBindingRes> result = clientService.checkBinding(cbReq);
        return result.getResult().getGroupId();
    }
}
