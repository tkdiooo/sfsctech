package com.sfsctech.common.core.base.domain.result;

import com.sfsctech.common.core.base.constants.RpcConstants.Status;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Class BasicResult
 *
 * @author 张麒 2017/3/21.
 * @version Description:
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态
     */
    private boolean success = true;
    /**
     * 响应状态
     */
    protected Status status = Status.Successful;
    /**
     * 响应消息列表
     */
    private List<String> messages = new ArrayList<>();

    public BaseResult() {
    }

    public BaseResult(boolean success, Status status, String... messages) {
        this.success = success;
        this.status = status;
        if (ArrayUtils.isNotEmpty(messages)) {
            this.messages.addAll(Arrays.asList(messages));
        }
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

    public void setMessage(String messages) {
        this.messages.clear();
        this.addMessage(messages);
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    /**
     * Attachment
     */
    private Map<String, Object> attachs;

    public void addAttach(String key, Object value) {
        if (null == this.attachs) {
            this.attachs = new HashMap<>();
        }
        this.attachs.put(key, value);
    }

    public void setAttachs(Map<String, Object> map) {
        this.attachs = map;
    }

    public Map<String, Object> getAttachs() {
        return this.attachs;
    }

}
