package com.sfsctech.core.base.domain.result;

import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.ex.GenericException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * RPC服务通信接口响应对象
 *
 * @author 张麒 2018-5-24.
 * @version Description:
 */
public class RpcResult<T> extends BaseResult {

    private static final long serialVersionUID = -4406532034304920638L;

    private T result;
    private GenericException exception;

    public RpcResult() {
        super();
    }

    public RpcResult(T result) {
        super();
        this.result = result;
    }

    public RpcResult(T result, GenericException exception) {
        super();
        this.result = result;
        this.exception = exception;
    }

    public RpcResult(T result, RpcConstants.Status status, GenericException exception) {
        super(status);
        this.result = result;
        this.exception = exception;
    }

    public RpcResult(RpcConstants.Status status) {
        super(status);
    }

    public RpcResult(RpcConstants.Status status, GenericException exception) {
        super(status);
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public GenericException getException() {
        return exception;
    }

    public void setException(GenericException exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        super.getMessages().clear();
        super.addMessages(message);
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        list.add("statusCode");
        if (!(null != super.getAttachs() && super.getAttachs().size() > 0)) {
            list.add("attachs");
        }
        if (!(null != super.getMessages() && super.getMessages().size() > 0)) {
            list.add("messages");
        }
        if (null == this.exception) {
            list.add("exception");
        }
        if (null == this.result) {
            list.add("result");
        }
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).setExcludeFieldNames(list.toArray(new String[]{})).toString();
    }
}
