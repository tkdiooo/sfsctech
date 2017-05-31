package com.sfsctech.framework.inf;

import com.sfsctech.common.base.model.PagingInfo;
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

    RpcResult<Long> save(List<SysAccountDto> dataSet);

    RpcResult<SysAccountDto> find();

    RpcResult<SysAccountDto> findByAccount(String account);

    RpcResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo);
}
