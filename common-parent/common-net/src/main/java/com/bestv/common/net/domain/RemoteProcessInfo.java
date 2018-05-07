//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.domain;

public class RemoteProcessInfo {
    private String flameId;
    private String spanId;
    private String serviceName;
    private String serviceMethod;
    private int serviceVersion;
    private String remoteType;
    private String syncType;
    private String serverHost;
    private int serverPort;
    private String vpcName;
    private String subnetName;
    private String profiler;
    private boolean success;
    private long processTime;

    public RemoteProcessInfo() {
    }

    public String getFlameId() {
        return this.flameId;
    }

    public void setFlameId(String flameId) {
        this.flameId = flameId;
    }

    public String getSpanId() {
        return this.spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMethod() {
        return this.serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public int getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(int serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getRemoteType() {
        return this.remoteType;
    }

    public void setRemoteType(String remoteType) {
        this.remoteType = remoteType;
    }

    public String getSyncType() {
        return this.syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getServerHost() {
        return this.serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getVpcName() {
        return this.vpcName;
    }

    public void setVpcName(String vpcName) {
        this.vpcName = vpcName;
    }

    public String getSubnetName() {
        return this.subnetName;
    }

    public void setSubnetName(String subnetName) {
        this.subnetName = subnetName;
    }

    public String getProfiler() {
        return this.profiler;
    }

    public void setProfiler(String profiler) {
        this.profiler = profiler;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getProcessTime() {
        return this.processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
