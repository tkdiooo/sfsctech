package com.sfsctech.core.base.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 9128475035980902677L;

    private UserAuthData userAuthData;

    private Map<String, Object> attribute = new HashMap<>();

    public void setUserAuthData(UserAuthData userAuthData) {
        this.userAuthData = userAuthData;
    }

    public UserAuthData getUserAuthData() {
        return userAuthData;
    }
    
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public Object getAttribute(String key) {
        return attribute.get(key);
    }

    public void setAttribute(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    public void setAttribute(String key, Object value) {
        attribute.put(key, value);
    }

    public void removeAttribute(String key) {
        attribute.remove(key);
    }

}
