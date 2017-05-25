package com.sfsctech.mybatis.service.read.impl;

import com.sfsctech.mybatis.domain.TSysAccount;
import com.sfsctech.mybatis.domain.TSysAccountExample;
import com.sfsctech.mybatis.mapper.TSysAccountMapper;
import com.sfsctech.mybatis.service.read.AccountReadService;
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
    public void find() {
        TSysAccountExample example = new TSysAccountExample();
        List<TSysAccount> list = mapper.selectByExample(example);
        System.out.println(list);
    }

    @Override
    public void findByAccount(String account) {

    }
}
