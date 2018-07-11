package com.sfsctech.dubbo.sso.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class SSOProperties
 *
 * @author 张麒 2018-7-11.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "website.sso.dubbo"
)
public class SSOProperties {

    private boolean lazy;
    private String version;
    private Integer timeout;

    public SSOProperties() {
    }


    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
