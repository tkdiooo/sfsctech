package com.sfsctech.base.exception;

import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.inf.IEnum;

import java.text.MessageFormat;

/**
 * Class BaseException
 *
 * @author 张麒 2017/5/5.
 * @version Description:
 */
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private IEnum<String, String> tips;
    private String[] params = new String[0];
    private String viewName = CommonConstants.VIEW_500;

    protected BaseException() {
        super();
    }

    protected BaseException(IEnum<String, String> tips, String... params) {
        super();
        this.tips = tips;
        if (null != params) this.params = params;
    }

    protected BaseException(String message, String... params) {
        super(message);
        if (null != params) this.params = params;
    }

    protected BaseException(String message, Throwable cause, String... params) {
        super(message, cause);
        if (null != params) this.params = params;
    }

    public IEnum<String, String> getTips() {
        return tips;
    }

    public void setTips(IEnum<String, String> tips) {
        this.tips = tips;
    }

    @Override
    public String getMessage() {
        if (params.length > 0) return MessageFormat.format(super.getMessage(), (Object[]) this.params);
        else return super.getMessage();
    }

    public String[] getParams() {
        return this.params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void addParams(String param) {
        this.params[params.length] = param;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
