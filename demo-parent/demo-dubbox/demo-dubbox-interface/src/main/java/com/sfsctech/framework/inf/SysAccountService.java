package com.sfsctech.framework.inf;

import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.framework.model.dto.SysAccountDto;

/**
 * Class SysAccountService
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public interface SysAccountService {

    RpcResult<SysAccountDto> save(SysAccountDto model);

    RpcResult<SysAccountDto> find();

    RpcResult<SysAccountDto> findByAccount(String account);

    RpcResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo);
}
