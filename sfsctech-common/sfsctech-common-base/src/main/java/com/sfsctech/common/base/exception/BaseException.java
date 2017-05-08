package com.sfsctech.common.base.exception;

import com.sfsctech.common.inf.IEnum;

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
    private String message;
    private String[] params = new String[0];

    public BaseException(IEnum<String, String> tips, String... params) {
        this.tips = tips;
        if (null != params) this.params = params;
    }

    public BaseException(String message, String... params) {
        this.message = message;
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
        if (params.length > 0) return MessageFormat.format(this.message, (Object[]) this.params);
        else return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
