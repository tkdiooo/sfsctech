package com.inf.dubbox;

import com.sfsctech.common.base.result.RpcResult;

/**
 * Class DubboxService
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
public interface DubboxService<T> {

    RpcResult<T> DubboxTestMethod(String... params);
}
