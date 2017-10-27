package com.sfsctech.website.jsp.service;

//import com.alibaba.dubbo.config.annotation.Reference;
import com.sfsctech.base.model.PagingInfo;
import com.sfsctech.rpc.result.ActionResult;
import com.sfsctech.common.util.RandomUtil;
import com.sfsctech.constants.StatusConstants;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.dto.SysAccountDto;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Class AccountService
 *
 * @author 张麒 2017/5/29.
 * @version Description:
 */
@Service
public class AccountService {

//    @Reference
    private SysAccountService accountService;

    public void batchSave() {
        SysAccountDto dto;
        for (int i = 0; i < 50; i++) {
            dto = new SysAccountDto();
            dto.setCreatetime(new Date());
            dto.setCreator((long) i);
            dto.setEnabled(0);
            dto.setLocked(0);
            dto.setStatus(StatusConstants.Status.Valid.getCode());
            dto.setAccount(RandomUtil.getRandom(RandomUtil.Strategy.Char, 15));
            dto.setPassword(RandomUtil.getRandom(RandomUtil.Strategy.Full, 10));
            System.out.println(accountService.save(dto));
        }
    }

    public ActionResult<SysAccountDto> save(SysAccountDto model) {
        model.setCreatetime(new Date());
        model.setCreator(1L);
        return accountService.save(model);
    }

    public ActionResult<PagingInfo<SysAccountDto>> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        return accountService.findByPage(pagingInfo);
    }
}
