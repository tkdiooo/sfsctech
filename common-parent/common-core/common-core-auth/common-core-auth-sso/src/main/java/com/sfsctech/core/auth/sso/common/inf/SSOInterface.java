package com.sfsctech.core.auth.sso.common.inf;


import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.auth.sso.common.jwt.JwtToken;

/**
 * Class SSOCheckInterface
 *
 * @author 张麒 2018-7-20.
 * @version Description:
 */
public interface SSOInterface {

    RpcResult<JwtToken> verifyToken(String token);

    RpcResult<JwtToken> refreshJwt();

}
