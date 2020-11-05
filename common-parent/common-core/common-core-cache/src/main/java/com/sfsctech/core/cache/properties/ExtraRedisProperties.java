package com.sfsctech.core.cache.properties;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Extra
 *
 * @author 张麒 2020-11-2.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "spring.redis"
)
public class ExtraRedisProperties {

    private Map<String, RedisProperties> extra = new HashMap<>();

    public Map<String, RedisProperties> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, RedisProperties> extra) {
        this.extra = extra;
    }
}
