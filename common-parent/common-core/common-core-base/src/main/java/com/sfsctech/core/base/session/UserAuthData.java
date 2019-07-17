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

    public UserAuthData(String account, String password) {
        this.account = account;
        this.password = password;
    }

    private final String account;

    private final String password;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

}
