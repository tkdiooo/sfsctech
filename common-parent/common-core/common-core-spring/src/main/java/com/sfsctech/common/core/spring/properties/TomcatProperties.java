package com.sfsctech.common.core.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class TomcatProperties
 *
 * @author 张麒 2017/9/14.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "server.tomcat"
)
public class TomcatProperties {

    private boolean optimize;
    private String connector;
    private Integer maxKeepAliveRequests;
    private Integer connectionTimeout;
    private Integer keepAliveTimeout;

    public TomcatProperties() {

    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public Integer getMaxKeepAliveRequests() {
        return maxKeepAliveRequests;
    }

    public void setMaxKeepAliveRequests(Integer maxKeepAliveRequests) {
        this.maxKeepAliveRequests = maxKeepAliveRequests;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(Integer keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }
}
