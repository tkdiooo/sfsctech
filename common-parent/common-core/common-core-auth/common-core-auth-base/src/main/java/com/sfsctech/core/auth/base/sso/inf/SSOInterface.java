package com.sfsctech.core.auth.base.sso.inf;


import com.sfsctech.core.auth.base.sso.jwt.AccessJwtToken;
import com.sfsctech.core.base.domain.result.RpcResult;

/**
 * Class SSOCheckInterface
 *
 * @author 张麒 2018-7-20.
 * @version Description:
 */
public interface SSOInterface {

    RpcResult<AccessJwtToken> verifyToken(String token);

    RpcResult<AccessJwtToken> refreshJwt();

}
