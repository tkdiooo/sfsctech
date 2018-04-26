package com.sfsctech.authorize.base.inf;

import com.sfsctech.base.jwt.JwtToken;
import com.sfsctech.base.session.UserAuthData;
import com.sfsctech.rpc.result.ActionResult;

/**
 * Class LoginService
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
public interface LoginService {

    /**
     * 登录服务
     *
     * @param authData UserAuthData
     * @return ActionResult&lt;JwtToken&gt;
     */
    ActionResult<JwtToken> login(UserAuthData authData);

    /**
     * 登出服务
     *
     * @param jt JwtToken
     */
    void logout(JwtToken jt);
}
