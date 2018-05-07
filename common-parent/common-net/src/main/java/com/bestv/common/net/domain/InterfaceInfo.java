//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.domain;

import java.util.List;

public class InterfaceInfo {
    private String appCode;
    private String appName;
    private Class interfaceClass;
    private List<ServicePointInfo> servicePointInfos;

    public InterfaceInfo() {
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Class getInterfaceClass() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public List<ServicePointInfo> getServicePointInfos() {
        return this.servicePointInfos;
    }

    public void setServicePointInfos(List<ServicePointInfo> servicePointInfos) {
        this.servicePointInfos = servicePointInfos;
    }
}
