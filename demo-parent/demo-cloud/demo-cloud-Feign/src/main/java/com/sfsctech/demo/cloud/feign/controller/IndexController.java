package com.sfsctech.demo.cloud.feign.controller;

import com.alibaba.fastjson.JSON;
import com.sfsctech.core.rpc.result.ActionResult;
import com.sfsctech.demo.cloud.feign.service.IndexService;
import com.sfsctech.demo.cloud.inf.request.CheckBindingReq;
import com.sfsctech.demo.cloud.inf.request.CheckBindingRes;
import com.sfsctech.demo.cloud.inf.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger loggers = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService service;

    @Autowired
    private ClientService clientService;

    @GetMapping("index")
    public String index(@RequestParam String name) {
        CheckBindingReq cbReq = new CheckBindingReq();
        cbReq.setBuCode(name);
        loggers.info(JSON.toJSONString(cbReq));
        loggers.warn(JSON.toJSONString(cbReq));
        loggers.error(JSON.toJSONString(cbReq));
        ActionResult<CheckBindingRes> result = clientService.checkBinding(cbReq);
        loggers.info(JSON.toJSONString(result));
        return result.getResult().getGroupId();
    }
}
