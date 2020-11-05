package com.sfsctech.core.cache.factory;


import com.sfsctech.core.cache.redis.RedisCacheClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class CacheFactory
 *
 * @author 张麒 2017/6/30.
 * @version Description:
 */
public class CacheFactory<I extends CacheProxy<String, Object>> implements CacheProxyFactory {

    private I cacheProxy;

    private Map<String, I> extraCacheProxy;

    public CacheFactory(I cacheProxy, Map<String, I> extraCacheProxy) {
        this.cacheProxy = cacheProxy;
        this.extraCacheProxy = extraCacheProxy;
    }

    @Override
    public I getCacheClient() {
        return cacheProxy;
    }

    @Override
    public I getExtraCacheClient(String key) {
        return extraCacheProxy.get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T get(String key) {
        return (T) getCacheClient().get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> getList(String key) {
        return (List<T>) getCacheClient().get(key);
    }

    public String buildCacheKey(String... keys) {
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (sb.length() != 0) {
                sb.append(":");
            }
            sb.append(key);
        }
        return sb.toString();
    }
}
