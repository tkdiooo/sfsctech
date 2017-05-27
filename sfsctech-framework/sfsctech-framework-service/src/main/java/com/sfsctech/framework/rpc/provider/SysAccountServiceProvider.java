package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.dto.SysAccountDto;

/**
 * Class SysAccountServiceProvider
 *
 * @author 张麒 2017/5/27.
 * @version Description:
 */
@Service
public class SysAccountServiceProvider implements SysAccountService{

    @Override
    public RpcResult<SysAccountDto> save() {
        return null;
    }

    @Override
    public RpcResult<SysAccountDto> find() {
        return null;
    }

    @Override
    public RpcResult<SysAccountDto> findByAccount(String account) {
        return null;
    }
}
