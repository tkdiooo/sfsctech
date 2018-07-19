package com.sfsctech.dubbo.sso.util;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.dubbo.base.inf.VerifyService;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil extends com.sfsctech.core.auth.sso.util.SingletonUtil{

    private static VerifyService verifyService;

    public static VerifyService getVerifyService() {
        if (null == verifyService)
            synchronized (VerifyService.class) {
                if (null == verifyService)
                    verifyService = (VerifyService) SpringContextUtil.getBean(ReferenceConfig.class).get();
            }
        return verifyService;
    }
}
