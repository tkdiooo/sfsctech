package com.sfsctech.spring.properties;

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
}
