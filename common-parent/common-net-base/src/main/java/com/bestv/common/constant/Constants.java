//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.constant;

import com.bestv.common.lang.ex.CommonErrorCode;
import com.bestv.common.lang.ex.FastGenericException;
import com.bestv.common.lang.ex.GenericException;
import java.nio.charset.Charset;

public final class Constants {
    public static final String DEFAULT_ERROR_BUILDER_ERROR_CODE_PREFIX = "CP";
    public static final String DEFAULT_ERROR_BUILDER_ERROR_CODE_VERSION = "1";
    public static final Integer DEFAULT_ERROR_BUILDER_STACK_DEPTH = 0;
    public static final Integer DEFAULT_ERROR_BUILDER_STACK_LENGTH = 1000;
    public static final String DEFAULT_CHARSET_NAME = "utf-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
    public static final String COMMON_SCENARIO_CODE = "0000";
    public static final String COMMON_SCENARIO_DESCRIPTION = "通用打印场景";
    public static final String UNKNOWN_ERROR_CODE = "999";
    public static final String UNKNOWN_ERROR_DESCRIPTION = "未知异常";
    public static final GenericException ILLEGAL_METHOD_ERROR;
    public static final GenericException UNKNOWN_ERROR;
    public static final String FOR_NULL_STRING = "★☆★☆★★☆★★☆★★☆★☆★★☆★★☆★☆★★☆★★☆★★☆★☆★★☆★★☆★";

    public Constants() {
    }

    static {
        ILLEGAL_METHOD_ERROR = new FastGenericException(CommonErrorCode.UNKNOWN_ERROR, "非法的方法");
        UNKNOWN_ERROR = new FastGenericException(CommonErrorCode.UNKNOWN_ERROR, "未知异常");
    }
}
