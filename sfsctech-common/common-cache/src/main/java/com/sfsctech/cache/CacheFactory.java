package com.sfsctech.cache;

import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.cache.redis.inf.IRedisService;

/**
 * Class CacheFactory
 *
 * @author 张麒 2017/6/30.
 * @version Description:
 */
public class CacheFactory implements ICacheFactory<String, Object> {

    private IRedisService<String, Object> cacheProxy;

    public CacheFactory(IRedisService<String, Object> cacheProxy) {
        this.cacheProxy = cacheProxy;
    }

    @Override
    public ICacheService<String, Object> getCacheClient() {
        return cacheProxy;
    }
}
