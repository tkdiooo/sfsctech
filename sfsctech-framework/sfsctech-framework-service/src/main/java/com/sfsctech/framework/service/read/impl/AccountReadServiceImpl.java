package com.sfsctech.framework.service.read.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfsctech.common.base.model.PagingInfo;
import com.sfsctech.framework.mapper.TSysAccountMapper;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.domain.TSysAccountExample;
import com.sfsctech.framework.model.dto.SysAccountDto;
import com.sfsctech.framework.service.read.AccountReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class AccountReadServiceImpl
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
@Service
public class AccountReadServiceImpl implements AccountReadService {

    @Autowired
    private TSysAccountMapper mapper;

    @Override
    public List<TSysAccount> find() {
        return mapper.selectByExample(new TSysAccountExample());
    }

    @Override
    public List<TSysAccount> findByAccount(String account) {
        TSysAccountExample example = new TSysAccountExample();
        TSysAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TSysAccount> findByPage(PagingInfo<SysAccountDto> pagingInfo) {
        PageHelper.startPage(pagingInfo.getPageNum(), pagingInfo.getPageSize());
        return new PageInfo<>(find());
    }
}
