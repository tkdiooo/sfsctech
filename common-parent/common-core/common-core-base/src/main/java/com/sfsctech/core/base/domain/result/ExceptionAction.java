package com.sfsctech.core.base.domain.result;

import com.sfsctech.core.base.enums.StatusEnum;
import com.sfsctech.core.base.ex.GenericException;

/**
 * Class ExceptionAction
 *
 * @author 张麒 2020-12-30.
 * @version Description:
 */
public class ExceptionAction extends BaseResult {

    private static final long serialVersionUID = 2705843772807447979L;

    private GenericException exception;

    public ExceptionAction() {
        super();
    }

    public ExceptionAction(StatusEnum<Integer, String, Boolean> status) {
        super(status);
    }

    public ExceptionAction(StatusEnum<Integer, String, Boolean> status, GenericException exception) {
        super(status);
        this.exception = exception;
    }

    public ExceptionAction(StatusEnum<Integer, String, Boolean> status, String... messages) {
        super(status, messages);
    }

    public GenericException getException() {
        return exception;
    }

    public void setException(GenericException exception) {
        this.exception = exception;
    }

    @Override
    public void setStatus(StatusEnum<Integer, String, Boolean> status) {
        super.setCode(status.getCode());
        super.setSuccess(status.getSuccessful());
        addMessages(status.getDescription());
    }
}
