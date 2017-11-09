package com.sfsctech.cache;

import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.cache.redis.inf.IRedisService;

import java.util.List;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T get(String key) {
        return (T) getCacheClient().get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> getList(String key) {
        return (List<T>) getCacheClient().get(key);
    }
}
