package com.sfsctech.cloud.base.inf;


import com.sfsctech.cloud.base.annotation.CloudService;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.auth.sso.server.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class LoginService
 *
 * @author 张麒 2017/10/8.
 * @version Description:
 */
@RestController
@CloudService("sso-server")
public interface LoginService {

    /**
     * 登录服务
     *
     * @param authData UserAuthData
     * @return ActionResult&lt;JwtToken&gt;
     */
    @RequestMapping("login")
    RpcResult<JwtToken> login(@RequestBody UserAuthData authData);

    /**
     * 登出服务
     *
     * @param jt JwtToken
     */
    @RequestMapping("logout")
    void logout(@RequestBody JwtToken jt);
}
