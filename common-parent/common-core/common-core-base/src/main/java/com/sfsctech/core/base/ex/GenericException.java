package com.sfsctech.core.base.ex;

import com.sfsctech.core.base.constants.CommonConstants;

import java.text.MessageFormat;

/**
 * Class BaseException
 *
 * @author 张麒 2017/5/5.
 * @version Description:
 */
public class GenericException extends RuntimeException implements BaseException<ExceptionTips<String, String>> {

    private static final long serialVersionUID = -3700435164531898375L;

    private ExceptionTips<String, String> tips;
    private String[] params = new String[0];
    private String viewName = CommonConstants.VIEW_500;

    public GenericException() {
        super();
    }

    public GenericException(ExceptionTips<String, String> tips) {
        super();
        this.tips = tips;
    }

    public GenericException(String message, String... params) {
        super(message);
        if (null != params) this.params = params;
    }

    public GenericException(String message, Throwable cause, String... params) {
        super(message, cause);
        if (null != params) this.params = params;
    }

    @Override
    public ExceptionTips<String, String> getTips() {
        return tips;
    }

    @Override
    public void setTips(ExceptionTips<String, String> tips) {
        this.tips = tips;
    }

    @Override
    public String getMessage() {
        if (params.length > 0) return MessageFormat.format(super.getMessage(), (Object[]) this.params);
        else if (null != this.tips)
            return "{message:" + tips.getDescription() + ",level:" + tips.getLevel().getDescription() + ",type:" + tips.getType().getDescription() + "}";
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

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
