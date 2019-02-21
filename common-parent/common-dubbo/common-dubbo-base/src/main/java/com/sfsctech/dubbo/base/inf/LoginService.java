package com.sfsctech.dubbo.base.inf;


import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.auth.sso.server.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;

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
    RpcResult<JwtToken> login(UserAuthData authData);

    /**
     * 登出服务
     *
     * @param jt JwtToken
     */
    void logout(JwtToken jt);
}
