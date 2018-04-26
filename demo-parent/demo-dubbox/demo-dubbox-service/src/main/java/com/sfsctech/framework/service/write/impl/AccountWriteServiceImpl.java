package com.sfsctech.framework.service.write.impl;

import com.sfsctech.framework.mapper.TSysAccountMapper;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.service.write.AccountWriteService;
import com.sfsctech.mybatis.annotation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class AccountWriteServiceImpl
 *
 * @author 张麒 2017/5/28.
 * @version Description:
 */
@Service
@DataSource
public class AccountWriteServiceImpl implements AccountWriteService {

    @Autowired
    private TSysAccountMapper accountMapper;

    @Override
    public Long save(TSysAccount account) {
        accountMapper.insert(account);
        return account.getGuid();
    }
}
