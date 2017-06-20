package com.sfsctech.framework.inf;

import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.base.result.ActionResult;
import com.sfsctech.framework.model.dto.SysAccountDto;

import java.util.List;

/**
 * Class SysAccountService
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public interface SysAccountService {

    ActionResult<SysAccountDto> save(SysAccountDto model);

    ActionResult<SysAccountDto> find();

    ActionResult<SysAccountDto> findByAccount(String account);

    ActionResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo);
}
