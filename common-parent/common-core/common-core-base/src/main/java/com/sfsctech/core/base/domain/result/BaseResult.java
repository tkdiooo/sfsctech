package com.sfsctech.core.base.domain.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.sfsctech.core.base.constants.RpcConstants.Status;
import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.enums.StatusEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

/**
 * Class BasicResult
 *
 * @author 张麒 2017/3/21.
 * @version Description:
 */
public class BaseResult extends BaseDto {

    private static final long serialVersionUID = -8243241537328556858L;
    /**
     * 响应状态
     */
    private boolean success = true;
    /**
     * 响应状态
     */
    protected StatusEnum<Integer, String, Boolean> status = Status.Successful;
    /**
     * 响应消息列表
     */
    private List<String> messages = new ArrayList<>();

    public BaseResult() {
    }

    public BaseResult(StatusEnum<Integer, String, Boolean> status, String... messages) {
        this.success = status.getSuccessful();
        this.status = status;
        if (ArrayUtils.isNotEmpty(messages)) {
            addMessages(messages);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean hasErrors) {
        this.success = hasErrors;
    }

    @JSONField(name = "statusCode")
    public int getStatusCode() {
        return status.getCode();
    }

    @JSONField(name = "statusCode")
    public void setStatusCode(int code) {
        this.status = Status.getEnum(code);
    }

    @JSONField(deserialize = false)
    public StatusEnum<Integer, String, Boolean> getStatus() {
        return status;
    }

    @JSONField(deserialize = false)
    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessages(String... messages) {
        this.messages.addAll(Arrays.asList(messages));
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

    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)).setExcludeFieldNames("statusCode").toString();
    }
}
