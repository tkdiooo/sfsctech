package com.sfsctech.core.auth.sso.inf;


import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;

/**
 * Class SSOCheckInterface
 *
 * @author 张麒 2018-7-20.
 * @version Description:
 */
public interface SSOCheckInterface {

    RpcResult<JwtToken> simpleVerify(JwtToken jt);


    RpcResult<JwtToken> complexVerify(JwtToken jt);

}
