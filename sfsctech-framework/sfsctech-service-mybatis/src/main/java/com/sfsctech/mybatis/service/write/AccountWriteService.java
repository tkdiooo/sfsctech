package com.sfsctech.mybatis.service.write;

import com.sfsctech.mybatis.domain.TSysAccount;

/**
 * Class AccountWriteService
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
public interface AccountWriteService {

    Long save(TSysAccount account);
}
