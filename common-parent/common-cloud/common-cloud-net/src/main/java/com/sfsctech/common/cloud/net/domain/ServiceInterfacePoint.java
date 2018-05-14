package com.sfsctech.common.cloud.net.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Method;

/**
 * Class ServiceInterfacePoint
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */
public class ServiceInterfacePoint {
    // 接口方法
    private Method method;
    // 返回对象
    private Class<?> result;
    // 参数对象
    private Class params;
    // 服务路径
    private String serviceUrl;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getResult() {
        return result;
    }

    public void setResult(Class<?> result) {
        this.result = result;
    }

    public Class getParams() {
        return params;
    }

    public void setParams(Class params) {
        this.params = params;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
