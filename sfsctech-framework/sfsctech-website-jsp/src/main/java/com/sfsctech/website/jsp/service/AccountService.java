package com.sfsctech.website.jsp.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfsctech.common.base.result.RpcResult;
import com.sfsctech.common.constants.StatusConstants;
import com.sfsctech.common.util.RandomUtil;
import com.sfsctech.framework.inf.SysAccountService;
import com.sfsctech.framework.model.dto.SysAccountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class AccountService
 *
 * @author 张麒 2017/5/29.
 * @version Description:
 */
@Service
public class AccountService {

    @Reference
    private SysAccountService accountService;


    public void batchSave() {
        List<SysAccountDto> dataSet = new ArrayList<>();
        SysAccountDto dto;
        for (int i = 0; i < 50; i++) {
            dto = new SysAccountDto();
            dto.setCreatetime(new Date());
            dto.setCreator((long) i);
            dto.setEnabled(0);
            dto.setLocked(0);
            dto.setStatus(StatusConstants.Status.VALID.getKey());
            dto.setAccount(RandomUtil.getRandom(RandomUtil.Strategy.Char, 15));
            dto.setPassword(RandomUtil.getRandom(RandomUtil.Strategy.Full, 10));
            dataSet.add(dto);
        }
        RpcResult<Long> result = accountService.save(dataSet);
        result.getDataSet().forEach(System.out::println);
    }

    public List<SysAccountDto> findByPage(int pageNum, int pageSize) {
        RpcResult<SysAccountDto> result = accountService.findByPage(pageNum, pageSize);
        return result.getDataSet();
    }
}
