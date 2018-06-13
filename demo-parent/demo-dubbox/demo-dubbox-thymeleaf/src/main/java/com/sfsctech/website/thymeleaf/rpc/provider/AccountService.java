package com.sfsctech.website.thymeleaf.rpc.provider;

import com.sfsctech.core.base.domain.model.PagingInfo;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.web.domain.result.ActionResult;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.dto.SysAccountDto;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Class AccountService
 *
 * @author 张麒 2017/9/14.
 * @version Description:
 */
@Service
public class AccountService {

//    @Reference
    private SysAccountService accountService;

    public ActionResult<SysAccountDto> save(SysAccountDto model) {
        model.setCreatetime(new Date());
        model.setCreator(1L);
        RpcResult<SysAccountDto> result = accountService.save(model);
        return ActionResult.forSuccess(result.getResult());
    }

    public ActionResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        RpcResult<PagingInfo<SysAccountDto>> result = accountService.findByPage(pagingInfo);
        return ActionResult.forSuccess(result.getResult());
    }
}
