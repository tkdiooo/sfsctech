package com.sfsctech.framework.service.read;

import com.sfsctech.framework.model.domain.TSysAccount;

import java.util.List;

/**
 * Class AccountReadService
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
public interface AccountReadService {

    List<TSysAccount> find();

    List<TSysAccount> findByAccount(String account);

    List<TSysAccount> findByPage(int pageNum, int pageSize);
}
