package com.sfsctech.security.csrf;

import com.sfsctech.common.uuid.UUIDUtil;

/**
 * Class CSRFToken
 *
 * @author 张麒 2017/7/20.
 * @version Description:
 */
public class CSRFToken {

    private String parameterName;
    private String token;

    public CSRFToken() {
        this.parameterName = UUIDUtil.base64Uuid();
        this.token = UUIDUtil.base58Uuid();
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
