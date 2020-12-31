package com.sfsctech.core.base.ex;

import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.json.FastJson;

import java.text.MessageFormat;

/**
 * Class RpcException
 *
 * @author 张麒 2018-3-5.
 * @version Description:
 */
public class RpcException extends RuntimeException implements BaseException<ExceptionTips<String, String>> {

    private static final long serialVersionUID = -5575991318962077661L;

    private ExceptionTips<String, String> tips;

    private String message;
    private String viewName = CommonConstants.VIEW_500;

    public RpcException() {
        super();
    }

    public RpcException(ExceptionTips<String, String> tips) {
        super();
        this.message = FastJson.toJSONString(tips);
    }

    public RpcException(ExceptionTips<String, String> tips, String... params) {
        super();
        this.message = FastJson.toJSONString(MessageFormat.format(tips.getDescription(), (Object[]) params));
    }

    @Override
    public ExceptionTips<String, String> getTips() {
        return tips;
    }

    @Override
    public void setTips(ExceptionTips<String, String> tips) {
        this.tips = tips;
    }

    public void setLocalizedMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
