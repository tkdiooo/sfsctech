package com.sfsctech.core.base.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.sfsctech.core.base.constants.RpcConstants;

import java.lang.reflect.Type;

/**
 * Class EnumValueDeserializer
 *
 * @author 张麒 2020-12-29.
 * @version Description:
 */
public class RpcStatusEnumDeserializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        final JSONObject jsonObject = parser.parseObject();
        return (T) RpcConstants.Status.getEnum(jsonObject.getInteger("code"));
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
