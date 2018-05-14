package com.sfsctech.common.core.base.domain.dto;

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

    private static final long serialVersionUID = 8799994997210907041L;

    private EnvContext envContext;

    public EnvContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(EnvContext envContext) {
        this.envContext = envContext;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
