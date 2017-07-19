package com.sfsctech.security.session;

import com.alibaba.fastjson.JSON;

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
        this.userAuthData = JSON.parseObject(userAuthInfo, UserAuthData.class);
    }

    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public void setAttribute(String key, Object value) {
        attribute.put(key, value);
    }

    public UserAuthData getUserAuthData() {
        return userAuthData;
    }

}
