package com.sfsctech.demo.cloud.inf.request;

import com.sfsctech.common.core.base.domain.dto.BaseDto;

/**
 * 检查是否绑定参数类
 *
 * @author: huangsheng
 * @date: 2017/11/2
 */
public class CheckBindingReq extends BaseDto {

    private static final long serialVersionUID = -7266885256350587240L;
    /**
     * 业务线memberId
     */
    private String buMemberId;

    /**
     * buCode
     */
    private String buCode;

    /**
     * Gets bu member id.
     *
     * @return the bu member id
     */
    public String getBuMemberId() {
        return buMemberId;
    }

    /**
     * Sets bu member id.
     *
     * @param buMemberId the bu member id
     */
    public void setBuMemberId(String buMemberId) {
        this.buMemberId = buMemberId;
    }

    /**
     * Gets bu code.
     *
     * @return the bu code
     */
    public String getBuCode() {
        return buCode;
    }

    /**
     * Sets bu code.
     *
     * @param buCode the bu code
     */
    public void setBuCode(String buCode) {
        this.buCode = buCode;
    }
}
