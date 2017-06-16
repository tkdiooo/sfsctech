package com.sfsctech.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class RedisProperties
 *
 * @author 张麒 2017/6/16.
 * @version Description:
 */
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
