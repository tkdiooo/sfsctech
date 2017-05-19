package com.sfsctech.mybatis.rpc.provider;

import com.inf.dubbox.DubboxService;
import com.sfsctech.common.base.result.RpcResult;
import org.springframework.stereotype.Service;

/**
 * Class DubboxServiceImpl
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
@Service
public class DubboxServiceImpl implements DubboxService {

    @Override
    public RpcResult DubboxTestMethod(String... params) {
        System.out.println("请求的参数长度" + params.length);
        for (String param : params) {
            System.out.println(param);
        }
        return new RpcResult();
    }

}
