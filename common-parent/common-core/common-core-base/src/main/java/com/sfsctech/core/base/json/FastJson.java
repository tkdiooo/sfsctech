package com.sfsctech.core.base.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sfsctech.core.base.constants.RpcConstants;

/**
 * Class JsonUtil
 *
 * @author 张麒 2017/9/1.
 * @version Description:
 */
public class FastJson {

    private static final SerializeConfig config;
    private static final SerializeFilter filter;

    static {
        config = new SerializeConfig();
        // TODO 需要添加动态配置枚举类
        config.configEnumAsJavaBean(RpcConstants.Status.class);
        filter = new FastJsonFilter();
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, SerializerFeature.WriteDateUseDateFormat);
    }

    public static String toStringByFilter(Object object) {
        return JSON.toJSONString(object, config, filter, SerializerFeature.WriteDateUseDateFormat);
    }

    public static SerializeConfig getSerializeConfig() {
        return config;
    }
}
