package com.sfsctech.common.base.exception;

import com.sfsctech.common.constants.I18NConstants.Tips;

/**
 * Class BizException
 *
 * @author 张麒 2016/4/8.
 * @version Description:
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = -2598217010733719435L;

    public BizException(Tips tips, String... params) {
        super(tips, params);
    }

    public BizException(String message, String... params) {
        super(message, params);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
