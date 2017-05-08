package com.sfsctech.common.base.result;

import java.util.HashMap;
import java.util.Map;

/**
 * Hibernate Validator验证响应对象
 *
 * @author 张麒 2017/3/21.
 * @version Description:
 */
public class ValidatorResult {

    private boolean hasErrors = false;

    private Map<String, String> messages = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

}
