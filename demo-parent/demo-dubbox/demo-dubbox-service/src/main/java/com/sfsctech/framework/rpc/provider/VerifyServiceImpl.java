package com.sfsctech.framework.rpc.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfsctech.core.auth.sso.inf.VerifyService;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.web.domain.result.ActionResult;

/**
 * Class VerifyServiceImpl
 *
 * @author 张麒 2018-6-1.
 * @version Description:
 */
@Service
public class VerifyServiceImpl implements VerifyService {

    @Override
    public ActionResult<JwtToken> simpleVerify(JwtToken jwtToken) {
        return null;
    }

    @Override
    public ActionResult<JwtToken> complexVerify(JwtToken jwtToken) {
        return null;
    }
}
