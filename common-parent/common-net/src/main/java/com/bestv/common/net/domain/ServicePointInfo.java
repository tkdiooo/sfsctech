//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.domain;

import java.lang.reflect.Method;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class ServicePointInfo {
    protected Class interfaceClass;
    protected Method serviceClientPoint;
    protected String serviceServerPoint;
    protected Class resultType;
    protected Class argumentType;

    public ServicePointInfo() {
    }

    public Class getInterfaceClass() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Method getServiceClientPoint() {
        return this.serviceClientPoint;
    }

    public void setServiceClientPoint(Method serviceClientPoint) {
        this.serviceClientPoint = serviceClientPoint;
    }

    public String getServiceServerPoint() {
        return this.serviceServerPoint;
    }

    public void setServiceServerPoint(String serviceServerPoint) {
        this.serviceServerPoint = serviceServerPoint;
    }

    public Class getResultType() {
        return this.resultType;
    }

    public void setResultType(Class resultType) {
        this.resultType = resultType;
    }

    public Class getArgumentType() {
        return this.argumentType;
    }

    public void setArgumentType(Class argumentType) {
        this.argumentType = argumentType;
    }

    public String toString() {
        return (new ToStringBuilder(this)).append("serviceClientPoint", this.serviceClientPoint).append("serviceServerPoint", this.serviceServerPoint).append("resultType", this.resultType).append("argumentType", this.argumentType).toString();
    }
}
