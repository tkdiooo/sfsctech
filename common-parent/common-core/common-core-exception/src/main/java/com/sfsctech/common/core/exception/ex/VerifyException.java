package com.sfsctech.common.core.exception.ex;

import com.sfsctech.common.core.base.domain.result.ValidatorResult;
import com.sfsctech.common.core.base.ex.ExceptionTips;
import com.sfsctech.common.core.base.ex.GenericException;

/**
 * Class ValidatorException
 *
 * @author 张麒 2017/3/30.
 * @version Description:
 */
public class VerifyException extends GenericException {

    private static final long serialVersionUID = -3534275511138703794L;

    private ValidatorResult result;

    public VerifyException() {
        super();
    }

    public VerifyException(ExceptionTips tips, String... params) {
        super(tips, params);
    }

    public VerifyException(String message, String... params) {
        super(message, params);
    }

    public VerifyException(ExceptionTips tips, ValidatorResult result, String... params) {
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

}
