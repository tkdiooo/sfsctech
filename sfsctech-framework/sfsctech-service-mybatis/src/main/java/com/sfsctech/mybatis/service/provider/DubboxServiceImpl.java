package com.sfsctech.mybatis.service.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.inf.dubbox.DubboxService;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.mybatis.service.read.AccountReadService;
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
//        readService.find();
        System.out.println("请求的参数长度" + params.length);
        for (String param : params) {
            System.out.println(param);
        }
//        RpcResult<TSysAccount> result = new RpcResult<>();
//        result.setDataSet(readService.find());
        return new RpcResult();
    }

}
