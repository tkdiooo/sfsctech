package com.sfsctech.cloud.sso.provider;

import com.sfsctech.core.auth.sso.base.inf.SSOInterface;
import com.sfsctech.core.auth.sso.base.jwt.AccessJwtToken;
import com.sfsctech.core.base.domain.result.RpcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * Class SSOProvider
 *
 * @author 张麒 2019-4-9.
 * @version Description:
 */
public class SSOProvider implements SSOInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public RpcResult<AccessJwtToken> verify(String token) {
        return new RpcResult<>();
    }

    @Override
    public RpcResult<AccessJwtToken> refresh(String token) {
        System.out.println(restTemplate);
        return new RpcResult<>();
    }
}
