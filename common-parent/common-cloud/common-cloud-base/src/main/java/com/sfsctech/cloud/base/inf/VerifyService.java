package com.sfsctech.cloud.base.inf;


import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.auth.sso.common.jwt.JwtToken;
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
    @RequestMapping("simpleVerify")
    RpcResult<JwtToken> simpleVerify(@RequestBody JwtToken jt);

    /**
     * 复杂Session检验：校验token包含的数据，以及更新数据版本
     *
     * @param jt JwtToken
     * @return ActionResult&lt;JwtToken&gt;
     */
    @RequestMapping("complexVerify")
    RpcResult<JwtToken> complexVerify(@RequestBody JwtToken jt);

}
