package com.sfsctech.common.dubbox.filter;

import com.alibaba.dubbo.rpc.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class ExceptionFilter
 *
 * @author 张麒 2017/5/24.
 * @version Description:
 */
public class ExceptionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        HttpServletRequest request = (HttpServletRequest) RpcContext.getContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) RpcContext.getContext().getResponse();
        Result result = invoker.invoke(invocation);
        if (result.hasException()) {
            System.out.println(result);
            return new RpcResult(new com.sfsctech.common.base.result.RpcResult<>());
        }
        return result;
    }
}
