package com.sfsctech.security.session;

import com.sfsctech.constants.VerifyConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Map;

/**
 * Class UserAuthData
 *
 * @author 张麒 2017/7/19.
 * @version Description:
 */
public class UserAuthData {

    public UserAuthData(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @NotBlank
    @Length(min = 5, max = 20, message = VerifyConstants.Length)
    private String account;

    @NotBlank
    @Length(min = 6, max = 20, message = VerifyConstants.Length)
    private String password;

    private String sessionID;

    private Map<String, Integer> roles;

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

    public Map<String, Integer> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Integer> roles) {
        this.roles = roles;
    }
}
