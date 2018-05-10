package com.sfsctech.common.core.exception.ex;

import com.sfsctech.common.core.base.ex.ExceptionTips;
import com.sfsctech.common.core.base.ex.GenericException;

/**
 * Class BizException
 *
 * @author 张麒 2016/4/8.
 * @version Description:
 */
public class BizException extends GenericException {

    private static final long serialVersionUID = -2598217010733719435L;

    public BizException() {
        super();
    }

    public BizException(ExceptionTips<?, String> tips, String... params) {
        super(tips, params);
    }

    public BizException(String message, String... params) {
        super(message, params);
    }

    public BizException(String message, Throwable cause, String... params) {
        super(message, cause, params);
    }

}
