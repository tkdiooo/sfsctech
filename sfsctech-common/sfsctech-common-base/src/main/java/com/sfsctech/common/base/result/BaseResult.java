package com.sfsctech.common.base.result;

import com.sfsctech.common.constants.RpcConstants.ResponseCode;

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
    protected boolean success = true;
    /**
     * 响应代码
     */
    protected ResponseCode responseCode = ResponseCode.SC_OK;
    /**
     * 响应消息列表
     */
    protected List<String> messages = new ArrayList<>();

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode ResponseCode) {
        this.responseCode = ResponseCode;
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
