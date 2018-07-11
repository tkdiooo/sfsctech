package com.sfsctech.cloud.sso.util;

import com.sfsctech.cloud.base.inf.VerifyService;
import com.sfsctech.core.spring.util.SpringContextUtil;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil {

    private static VerifyService verifyService;

    public static VerifyService getVerifyService() {
        if (null == verifyService)
            synchronized (VerifyService.class) {
                if (null == verifyService)
                    verifyService = SpringContextUtil.getBean(VerifyService.class);
            }
        return verifyService;
    }
}
