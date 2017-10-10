package com.sfsctech.base.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 9128475035980902677L;

    private String userAuthInfo;

    private UserAuthData userAuthData;

    private Map<String, Object> attribute = new HashMap<>();

    public String getUserAuthInfo() {
        return userAuthInfo;
    }

    public void setUserAuthInfo(String userAuthInfo) {
        this.userAuthInfo = userAuthInfo;
//        this.userAuthData = JSON.parseObject(userAuthInfo, UserAuthData.class);
//        if (StringUtil.isNotBlank(this.userAuthData.getSessionData())) {
//            attribute.putAll(JSONObject.parseObject(this.userAuthData.getSessionData()));
//        }
    }

    public UserAuthData getUserAuthData() {
        return userAuthData;
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
