package com.sfsctech.dubbox.config;

import com.sfsctech.common.util.ArrayUtil;
import com.sfsctech.common.util.ListUtil;
import com.sfsctech.configurer.Registrar;
import com.sfsctech.constants.DubboConstants;
import com.sfsctech.constants.LabelConstants;

/**
 * Class DubboConfig
 *
 * @author 张麒 2017/5/21.
 * @version Description:
 */
public class DubboConfig {

    // dubbo 属性
    //-------------------------------------------------------------------------------------------
    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    public static String getKryoSerializePackagePath() {
        return ListUtil.toString(DubboConstants.KRYO_SERIALIZE_PACKAGE, LabelConstants.COMMA);
    }

    /**
     * dubbo - filter设置
     */
    private static String[] FILTERS = {"-exception", "ExceptionHandler"};

    public static String getFILTERS() {
        return ArrayUtil.toString(FILTERS, LabelConstants.COMMA);
    }

    public static void setFILTERS(String[] FILTERS) {
        DubboConfig.FILTERS = FILTERS;
    }

}
