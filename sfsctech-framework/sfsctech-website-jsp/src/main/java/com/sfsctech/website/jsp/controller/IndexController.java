package com.sfsctech.website.jsp.controller;

//import com.alibaba.dubbo.rpc.proxy.TraceIdUtil;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.website.jsp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("index.html")
    public String index() {
//        TraceIdUtil.setTraceId(UUID.randomUUID().toString());
//        System.out.println("gift consumer traceId：" + TraceIdUtil.getTraceId());
        logger.info("日志消息");
        PagingInfo<SysAccountDto> pagingInfo = new PagingInfo<>();
        pagingInfo.setStart(1);
        pagingInfo = accountService.findByPage(pagingInfo);
        System.out.println(pagingInfo);
        pagingInfo.getData().forEach(System.out::println);
        System.out.println("IndexController.index()");
        return "index";
    }
}
