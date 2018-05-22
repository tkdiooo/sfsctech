package com.sfsctech.cloud.sso.inf;


import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.core.rpc.result.ActionResult;

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
