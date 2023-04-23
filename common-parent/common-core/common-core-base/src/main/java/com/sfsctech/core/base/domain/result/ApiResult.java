package com.sfsctech.core.base.domain.result;

import com.sfsctech.core.base.domain.dto.BaseDto;
import com.sfsctech.core.base.enums.TipsEnum;

import java.util.*;

/**
 * Class ApiResult
 *
 * @author 张麒 2022-9-20.
 * @version Description:
 */
public class ApiResult<T> extends BaseDto {

    private static final long serialVersionUID = -382596990633097139L;

    public ApiResult() {
        super();
    }

    public ApiResult(T result, String... messages) {
        super();
        this.result = result;
    }

    public ApiResult(T result, TipsEnum<Integer, String> tipsEnum, String... messages) {
        super();
        this.result = result;
        this.setStatus(tipsEnum);
    }

    public void setStatus(TipsEnum<Integer, String> tipsEnum) {
        this.resultCode = String.valueOf(tipsEnum.getCode());
        this.resultMessage = tipsEnum.getDescription();
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

    /**
     * 响应消息列表
     */
    private List<String> messages = new ArrayList<>();

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
     * result
     */
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 返回码
     */
    private String resultCode = "1";

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 返回消息
     */
    private String resultMessage;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
