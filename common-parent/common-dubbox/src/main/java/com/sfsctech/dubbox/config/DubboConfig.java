package com.sfsctech.dubbox.config;

import com.sfsctech.common.util.ArrayUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.constants.LabelConstants;

/**
 * Class DubboConfig
 *
 * @author 张麒 2017/5/21.
 * @version Description:
 */
public class DubboConfig {
    /**
     * dubbo - filter设置
     */
    private static String[] FILTERS = {"-exception", "HystrixHandler", "ExceptionHandler"};

    public static String getFILTERS() {
        return ArrayUtil.toString(FILTERS, LabelConstants.COMMA);
    }

    public static void setFILTERS(String[] FILTERS) {
        DubboConfig.FILTERS = FILTERS;
    }

}
