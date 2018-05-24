package com.sfsctech.core.rpc.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Class RpcUtil
 *
 * @author 张麒 2016/9/1.
 * @version Description:
 */
public class RpcUtil {

    private static final ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return threadLocal.get();
    }

    public static void setRequest(final HttpServletRequest request) {
        threadLocal.set(request);
    }

    public static void clear() {
        threadLocal.set(null);
    }
}
