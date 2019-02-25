package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.framework.inf.DubboxService;
import com.sfsctech.framework.service.read.AccountReadService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class DubboxServiceImpl
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
@Service
public class DubboxServiceImpl implements DubboxService {

    @Autowired
    private AccountReadService readService;

    @Override
    public RpcResult DubboxTestMethod(String... params) {
        readService.find();
        System.out.println("请求的参数长度" + params.length);
        for (String param : params) {
            System.out.println(param);
        }
        return new RpcResult();
    }

}
