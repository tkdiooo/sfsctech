package com.sfsctech.framework.inf;


import com.sfsctech.core.rpc.result.ActionResult;

/**
 * Class DubboxService
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
public interface DubboxService<T> {

    ActionResult<T> DubboxTestMethod(String... params);
}
