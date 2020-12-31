package com.sfsctech.core.base.ex;

import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.base.json.FastJson;
import org.apache.commons.lang3.ArrayUtils;

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

    public GenericException(ExceptionTips<String, String> tips, String... params) {
        super();
        this.tips = tips;
        if (null != params) this.params = params;
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
        if (null != this.tips) {
            if (params.length > 0) {
                return FastJson.toJSONString(MessageFormat.format(tips.getDescription(), (Object[]) params));
            } else {
                return FastJson.toJSONString(tips);
            }
        } else if (params.length > 0) {
            return MessageFormat.format(super.getMessage(), (Object[]) this.params);
        } else return super.getMessage();
    }

    public String[] getParams() {
        return this.params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public void addParams(String param) {
        ArrayUtils.add(params, param);
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
