package com.sfsctech.cache;

import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.cache.properties.CacheProperties;
import com.sfsctech.cache.redis.JedisProxy;
import com.sfsctech.cache.redis.RedisProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class CacheFactory
 *
 * @author 张麒 2017/6/30.
 * @version Description:
 */
@Component
public class CacheFactory implements ICacheFactory<String, Object> {

    @Autowired
    private CacheProperties properties;

    @Autowired(required = false)
    private JedisProxy jedisProxy;

    @Autowired(required = false)
    private RedisProxy redisProxy;

    @Override
    public ICacheService<String, Object> getCacheClient() {
        if (null != properties.getProtocol()) {
            if (properties.getProtocol().equals("cluster")) {
                return jedisProxy;
            } else if (properties.getProtocol().equals("singleton")) {
                return redisProxy;
            }
        }
        return null;
    }
}
