package com.sfsctech.common.exception.util;

import com.sfsctech.common.base.exception.BaseException;
import com.sfsctech.common.constants.I18NConstants;
import com.sfsctech.common.exception.excp.BizException;
import com.sfsctech.common.util.ResourceUtil;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.common.util.ThrowableUtil;

/**
 * Class BizExcpUtil
 *
 * @author 张麒 2017/5/9.
 * @version Description:
 */
public class BizExcpUtil {

    /**
     * 抛出业务异常
     *
     * @param tips   Tips
     * @param params params
     */
    public static void throwBizException(I18NConstants.Tips tips, String... params) {
        throw new BizException(tips, params);
    }

    /**
     * 抛出业务异常
     *
     * @param message message
     * @param params  String[] params
     */
    public static void throwBizException(String message, String... params) {
        throw new BizException(message, params);
    }

    /**
     * 获取异常的Root Message
     *
     * @param e Throwable
     * @return Root Message
     */
    public static String getRootMessage(Throwable e) {
        return getRootMessage(e, null);
    }

    /**
     * 获取
     *
     * @param e
     * @param defaults
     * @return
     */
    public static String getRootMessage(Throwable e, String defaults) {
        if (e instanceof BaseException) {
            String msg;
            BaseException ext = (BaseException) e;
            if (null != ext.getTips()) {
                msg = ResourceUtil.getMessage(ext.getTips(), ext.getParams());
            } else {
                msg = ext.getMessage();
            }
            return StringUtil.isEmpty(msg) ? defaults : msg;
        } else {
            return ThrowableUtil.getRootMessage(e, defaults);
        }
    }
}
