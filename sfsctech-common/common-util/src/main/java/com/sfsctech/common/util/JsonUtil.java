package com.sfsctech.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Class JsonUtil
 *
 * @author 张麒 2017/9/1.
 * @version Description:
 */
public class JsonUtil {

    private static SerializeConfig config = SpringContextUtil.getBean(SerializeConfig.class);

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, SerializerFeature.WriteDateUseDateFormat);
    }
}
