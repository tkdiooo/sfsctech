package com.sfsctech.framework.dao.impl;

import com.sfsctech.framework.dao.AccountDao;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.mybatis.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Class AccountDaoImpl
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl<TSysAccount, Long> implements AccountDao {
}
