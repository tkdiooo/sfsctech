package com.sfsctech.common.base.exception;

import com.sfsctech.common.constants.I18NConstants.Tips;
import com.sfsctech.common.base.result.ValidatorResult;

/**
 * Class ValidatorException
 *
 * @author 张麒 2017/3/30.
 * @version Description:
 */
public class VerifyException extends BaseException {

    private static final long serialVersionUID = -3534275511138703794L;

    private ValidatorResult result;

    public VerifyException(Tips tips, String... params) {
        super(tips, params);
    }

    public VerifyException(String message, String... params) {
        super(message, params);
    }

    public VerifyException(Tips tips, ValidatorResult result, String... params) {
        super(tips, params);
        this.result = result;
    }

    public VerifyException(String message, ValidatorResult result, String... params) {
        super(message, params);
        this.result = result;
    }

    public ValidatorResult getResult() {
        return result;
    }

    public void setResult(ValidatorResult result) {
        this.result = result;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
