package com.sfsctech.website.thymeleaf.rpc.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.rpc.result.ActionResult;
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

    @Reference
    private SysAccountService accountService;

    public ActionResult<SysAccountDto> save(SysAccountDto model) {
        model.setCreatetime(new Date());
        model.setCreator(1L);
        return accountService.save(model);
    }

    public ActionResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        return accountService.findByPage(pagingInfo);
    }
}
