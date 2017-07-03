package com.sfsctech.cache;

import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
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
    private RedisProxy redisProxy;

    @Override
    public ICacheService<String, Object> getCacheClient() {
        return redisProxy;
    }
}
