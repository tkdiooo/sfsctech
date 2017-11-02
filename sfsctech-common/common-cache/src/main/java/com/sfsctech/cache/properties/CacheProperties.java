package com.sfsctech.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class CacheProperties
 *
 * @author 张麒 2017-11-2.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "spring.redis"
)
public class CacheProperties {

    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
