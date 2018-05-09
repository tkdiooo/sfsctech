package com.sfsctech.common.cloud.net.domain;

import java.util.List;

/**
 * Class ServiceInterface
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */
public class ServiceInterface {

    private String appCode;
    private String appName;
    private Class interfaceClass;
    private List<ServiceInterfacePoint> serviceInterfacePoint;

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

    public List<ServiceInterfacePoint> getServiceInterfacePoint() {
        return serviceInterfacePoint;
    }

    public void setServiceInterfacePoint(List<ServiceInterfacePoint> serviceInterfacePoint) {
        this.serviceInterfacePoint = serviceInterfacePoint;
    }
}
