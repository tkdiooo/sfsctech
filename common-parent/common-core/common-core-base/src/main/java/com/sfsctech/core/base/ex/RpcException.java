package com.sfsctech.core.base.ex;

import java.text.MessageFormat;

/**
 * Class RpcException
 *
 * @author 张麒 2018-3-5.
 * @version Description:
 */
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = -5575991318962077661L;

    private String message;

    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
        this.message = message;
    }

    public RpcException(ExceptionTips<String, String> tips, String... params) {
        super((null != params && params.length > 0) ? MessageFormat.format(tips.getDescription(), (Object[]) params) : tips.getDescription());
        this.message = super.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
