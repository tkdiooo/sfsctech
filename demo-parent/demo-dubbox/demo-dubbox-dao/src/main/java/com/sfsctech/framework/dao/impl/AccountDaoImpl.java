package com.sfsctech.framework.dao.impl;

import com.sfsctech.data.mybatis.annotation.Namespace;
import com.sfsctech.data.mybatis.dao.impl.BaseDaoImpl;
import com.sfsctech.framework.dao.AccountDao;
import com.sfsctech.framework.model.domain.TSysAccount;
import com.sfsctech.framework.model.domain.TSysAccountExample;
import org.springframework.stereotype.Repository;

/**
 * Class AccountDaoImpl
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
@Repository
@Namespace("com.sfsctech.framework.mapper.TSysAccountMapper")
public class AccountDaoImpl extends BaseDaoImpl<TSysAccount, Long, TSysAccountExample> implements AccountDao {

}
