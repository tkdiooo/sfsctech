package com.sfsctech.common.base.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Class BaseDto
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
public abstract class BaseDto implements IBaseDto, Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
