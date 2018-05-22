package com.sfsctech.core.base.session;

import com.sfsctech.core.base.domain.dto.BaseDto;

/**
 * Class UserAuthData
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class UserAuthData extends BaseDto {

    private static final long serialVersionUID = 909824046354265186L;

    public UserAuthData() {

    }

    public UserAuthData(String account, String password) {
        this.account = account;
        this.password = password;
    }

    private String account;

    private String password;

    private String sessionID;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
