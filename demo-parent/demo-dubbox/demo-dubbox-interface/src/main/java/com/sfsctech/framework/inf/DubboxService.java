package com.sfsctech.framework.inf;


import com.sfsctech.core.base.domain.result.RpcResult;

/**
 * Class DubboxService
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
public interface DubboxService<T> {

    RpcResult<T> DubboxTestMethod(String... params);
}
