package com.sfsctech.dubbo.sso.util;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sfsctech.core.auth.sso.inf.VerifyService;
import com.sfsctech.core.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil extends com.sfsctech.core.auth.sso.util.SingletonUtil {

    private static final Logger logger = LoggerFactory.getLogger(SingletonUtil.class);

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
