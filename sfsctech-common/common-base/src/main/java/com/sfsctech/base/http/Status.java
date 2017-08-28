package com.sfsctech.base.http;

import com.sfsctech.constants.RpcConstants.ResponseCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Class Status
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class Status implements Serializable {

    private static final long serialVersionUID = -3139661368812891814L;

    public Status(ResponseCode responseCode) {
        this.code = responseCode.getKey();
        this.content = responseCode.getValue();
    }

    public Status(int code, String content) {
        this.code = code;
        this.content = content;
    }

    private int code;
    private String content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
