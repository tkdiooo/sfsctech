package com.sfsctech.framework.service.transactional.impl;

import com.sfsctech.framework.mapper.TSysAccountMapper;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.service.transactional.AccountTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class AccountTransactionalServiceImpl
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
@Service
@Transactional
public class AccountTransactionalServiceImpl implements AccountTransactionalService {

    @Autowired
    private TSysAccountMapper accountMapper;

    @Override
    public Long save(TSysAccount account) {
        accountMapper.insert(account);
        if (account.getGuid() == 0) {
            throw new RuntimeException("sssss");
        }
        return account.getGuid();
    }
}
