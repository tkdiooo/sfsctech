package com.sfsctech.dubbo.base.inf;


import com.sfsctech.core.base.domain.result.RpcResult;
//import com.sfsctech.core.auth.sso.server.jwt.AccessJwtToken;

/**
 * Class VerifyService
 *
 * @author 张麒 2017/10/13.
 * @version Description:
 */
public interface VerifyService {

    /**
     * 简单Session校验：只校验token是否存在
     *
     * @param jt JwtToken
     * @return ActionResult&lt;JwtToken&gt;
     */
//    RpcResult<AccessJwtToken> simpleVerify(AccessJwtToken jt);

    /**
     * 复杂Session检验：校验token包含的数据，以及更新数据版本
     *
     * @param jt JwtToken
     * @return ActionResult&lt;JwtToken&gt;
     */
//    RpcResult<AccessJwtToken> complexVerify(AccessJwtToken jt);

}
