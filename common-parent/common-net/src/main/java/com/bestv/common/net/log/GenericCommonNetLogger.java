//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bestv.common.lang.request.BaseRequest;
import com.bestv.common.lang.result.BaseResult;
import org.slf4j.Logger;

public class GenericCommonNetLogger implements CommonNetLogger {
    private final Logger logger;
    private static final PropertyPreFilter PROPERTY_PRE_FILTER = new GenericCommonNetLoggerFilter();

    public GenericCommonNetLogger(Logger logger) {
        this.logger = logger;
    }

    public void logRequest(BaseRequest request) {
        this.logger.info(JSON.toJSONString(request, PROPERTY_PRE_FILTER, new SerializerFeature[0]));
    }

    public void logResult(BaseResult result) {
        this.logger.info(JSON.toJSONString(result, PROPERTY_PRE_FILTER, new SerializerFeature[0]));
    }

    public void info(String message) {
        this.logger.info(message);
    }

    public void info(String format, Object... message) {
        this.logger.info(format, message);
    }

    public void warn(String message) {
        this.logger.warn(message);
    }

    public void warn(String format, Object... message) {
        this.logger.warn(format, message);
    }

    public void error(String message) {
        this.logger.error(message);
    }

    public void error(String format, Object... message) {
        this.logger.error(format, message);
    }

    public void error(String message, Throwable throwable) {
        this.logger.error(message, throwable);
    }
}
