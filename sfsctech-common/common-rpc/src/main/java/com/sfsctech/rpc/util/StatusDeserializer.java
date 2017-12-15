package com.sfsctech.rpc.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.sfsctech.constants.RpcConstants;
import com.sfsctech.constants.StatusConstants;

import java.lang.reflect.Type;

/**
 * Class StatusDeserializer
 *
 * @author 张麒 2017-12-15.
 * @version Description:
 */
public class StatusDeserializer implements ObjectDeserializer {


    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONLexer lexer = parser.getLexer();
        int value = lexer.intValue();
        return (T) RpcConstants.Status.Successful;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
