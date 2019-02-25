package com.sfsctech.dubbo.sso.util;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sfsctech.core.base.domain.result.RpcResult;
//import com.sfsctech.core.auth.sso.server.jwt.AccessJwtToken;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.dubbo.base.inf.VerifyService;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil {//implements SSOAuthorizationInterface {

    private VerifyService verifyService;

    private VerifyService getVerifyService() {
        if (null == verifyService)
            synchronized (VerifyService.class) {
                if (null == verifyService)
                    verifyService = (VerifyService) SpringContextUtil.getBean(ReferenceConfig.class).get();
            }
        return verifyService;
    }

//    @Override
//    public RpcResult<AccessJwtToken> simpleVerify(AccessJwtToken jt) {
//        return getVerifyService().simpleVerify(jt);
//    }

//    @Override
//    public RpcResult<AccessJwtToken> complexVerify(AccessJwtToken jt) {
//        return getVerifyService().complexVerify(jt);
//    }
}
