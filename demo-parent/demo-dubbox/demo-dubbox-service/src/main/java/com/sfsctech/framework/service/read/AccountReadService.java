package com.sfsctech.framework.service.read;

import com.github.pagehelper.PageInfo;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.dto.SysAccountDto;

import java.util.List;

/**
 * Class AccountReadService
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
public interface AccountReadService {

    List<TSysAccount> find();

    List<TSysAccount> findByAccount(String account);

    PageInfo<TSysAccount> findByPage(PagingInfo<SysAccountDto> pagingInfo);
}
