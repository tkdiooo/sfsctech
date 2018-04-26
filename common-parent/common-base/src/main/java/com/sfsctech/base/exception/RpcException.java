package com.sfsctech.base.exception;

import com.sfsctech.constants.I18NConstants;

/**
 * Class RpcException
 *
 * @author 张麒 2018-3-5.
 * @version Description:
 */
public class RpcException extends BaseException {

    private static final long serialVersionUID = -5575991318962077661L;

    public RpcException() {
        super();
    }

    public RpcException(I18NConstants.Tips tips, String... params) {
        super(tips, params);
    }

    public RpcException(String message, String... params) {
        super(message, params);
    }

    public RpcException(String message, Throwable cause, String... params) {
        super(message, cause, params);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
