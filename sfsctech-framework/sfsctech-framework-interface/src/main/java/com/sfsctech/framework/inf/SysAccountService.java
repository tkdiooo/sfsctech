package com.sfsctech.framework.inf;

import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.framework.model.dto.SysAccountDto;

import java.util.List;

/**
 * Class SysAccountService
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public interface SysAccountService {

    RpcResult<SysAccountDto> save();

    RpcResult<SysAccountDto> find();

    RpcResult<SysAccountDto> findByAccount(String account);
}
