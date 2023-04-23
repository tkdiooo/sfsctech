package com.sfsctech.core.base.domain.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.sfsctech.core.base.constants.RpcConstants;
import com.sfsctech.core.base.enums.StatusEnum;
import com.sfsctech.core.base.ex.RpcException;
import com.sfsctech.core.base.json.RpcStatusEnumDeserializer;
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
    private RpcException exception;

    public RpcResult() {
        super();
    }

    public RpcResult(T result) {
        super();
        this.result = result;
    }

    public RpcResult(T result, RpcException exception) {
        super();
        this.result = result;
        this.exception = exception;
    }

    public RpcResult(T result, RpcConstants.Status status, RpcException exception) {
        super(status);
        this.result = result;
        this.exception = exception;
    }

    public RpcResult(RpcConstants.Status status) {
        super(status);
    }

    public RpcResult(RpcConstants.Status status, RpcException exception) {
        super(status);
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public RpcException getException() {
        return exception;
    }

    public void setException(RpcException exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        super.getMessages().clear();
        super.addMessages(message);
    }

    @Override
    public void setStatus(StatusEnum<Integer, String, Boolean> status) {
        super.setCode(status.getCode());
        super.setSuccess(status.getSuccessful());
        addMessages(status.getDescription());
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
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
