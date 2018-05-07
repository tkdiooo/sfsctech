//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.lang.ex;

import com.bestv.common.lang.enums.BaseEnum;
import com.bestv.common.lang.enums.InfoLevel;
import com.bestv.common.lang.enums.InfoType;

public interface CommonErrorCode<T extends CommonErrorCode> extends BaseEnum<T> {
    CommonErrorCode UNKNOWN_ERROR = DefaultErrorCodeEnum.UNKNOWN_ERROR;

    InfoType getInfoType();

    InfoLevel getInfoLevel();
}
