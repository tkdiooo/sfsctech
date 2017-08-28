package com.sfsctech.base.result;

import com.sfsctech.base.http.Status;
import com.sfsctech.constants.RpcConstants.ResponseCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class BasicResult
 *
 * @author 张麒 2017/3/21.
 * @version Description:
 */
public abstract class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态
     */
    private boolean success = true;
    /**
     * 响应代码
     */
    protected Status status = new Status(ResponseCode.SC_OK);
    /**
     * 响应消息列表
     */
    private List<String> messages = new ArrayList<>();

    public BaseResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean hasErrors) {
        this.success = hasErrors;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
