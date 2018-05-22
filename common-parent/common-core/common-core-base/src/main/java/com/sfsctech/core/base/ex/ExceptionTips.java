package com.sfsctech.core.base.ex;


import com.sfsctech.core.base.enums.BaseEnum;
import com.sfsctech.core.base.ex.enums.ExceptionLevelEnum;
import com.sfsctech.core.base.ex.enums.ExceptionTypeEnum;

/**
 * Class ErrorCode
 *
 * @author 张麒 2018-5-10.
 * @version Description:
 */
public interface ExceptionTips<K, V> extends BaseEnum<K, V> {

    ExceptionTypeEnum getType();

    ExceptionLevelEnum getLevel();
}
