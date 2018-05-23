package com.sfsctech.core.exception.ex;

import com.sfsctech.core.base.ex.ExceptionTips;
import com.sfsctech.core.base.ex.GenericException;

/**
 * Class RpcException
 *
 * @author 张麒 2018-3-5.
 * @version Description:
 */
public class RpcException extends GenericException {

    private static final long serialVersionUID = -5575991318962077661L;

    public RpcException() {
        super();
    }

    public RpcException(ExceptionTips<String, String> tips) {
        super(tips);
    }

    public RpcException(String message, String... params) {
        super(message, params);
    }

    public RpcException(String message, Throwable cause, String... params) {
        super(message, cause, params);
    }

}
