package com.sfsctech.rpc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.constants.RpcConstants;

/**
 * Class JsonUtil
 *
 * @author 张麒 2017/9/1.
 * @version Description:
 */
public class JsonUtil {

//    private static SerializeConfig config = SpringContextUtil.getBean(SerializeConfig.class);

    public static String toJSONString(Object object) {

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.configEnumAsJavaBean(RpcConstants.Status.class, RpcConstants.ServiceStatus.class, Faction.class);
        return JSON.toJSONString(object, serializeConfig);
    }
}
