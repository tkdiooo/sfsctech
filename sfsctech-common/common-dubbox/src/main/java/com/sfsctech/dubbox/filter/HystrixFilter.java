package com.sfsctech.dubbox.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.*;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.dubbox.hystrix.DubboHystrixCommand;

/**
 * Class HystrixFilter
 *
 * @author 张麒 2018-3-1.
 * @version Description:
 */
public class HystrixFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        // 是否启用hystrix
//        if (!hystrixIsOpen) {
//            return invoker.invoke(invocation);
//        }
//        String group = invoker.getUrl().getParameter(HystrixConstants.GROUP_KEY);
//        URL url = invoker.getUrl();
//        // 未配置groupKey的接口不进行限流
//        if (StringUtil.isBlank(group)) {
//            group = invoker.getUrl().getParameter(Constants.ID_KEY);
//        }
        DubboHystrixCommand command = new DubboHystrixCommand(invoker, invocation);
        return command.execute();
    }
}
