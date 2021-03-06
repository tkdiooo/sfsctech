package com.sfsctech.cloud.sso.inf;

import com.sfsctech.cloud.base.annotation.CloudService;
//import com.sfsctech.core.auth.sso.base.jwt.AccessJwtToken;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class VerifyService
 *
 * @author 张麒 2017/10/13.
 * @version Description:
 */
@RestController
@CloudService("sso-server")
public interface VerifyService {

    /**
     * 简单Session校验：只校验token是否存在
     *
     * @param jt JwtToken
     * @return ActionResult&lt;JwtToken&gt;
     */
//    @RequestMapping("simpleVerify")
//    RpcResult<AccessJwtToken> simpleVerify(@RequestBody AccessJwtToken jt);

    /**
     * 复杂Session检验：校验token包含的数据，以及更新数据版本
     *
     * @param jt JwtToken
     * @return ActionResult&lt;JwtToken&gt;
     */
//    @RequestMapping("complexVerify")
//    RpcResult<AccessJwtToken> complexVerify(@RequestBody AccessJwtToken jt);

}
