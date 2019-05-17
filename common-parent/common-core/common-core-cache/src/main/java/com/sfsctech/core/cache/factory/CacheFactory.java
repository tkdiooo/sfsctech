package com.sfsctech.core.cache.factory;


import java.util.List;

/**
 * Class CacheFactory
 *
 * @author 张麒 2017/6/30.
 * @version Description:
 */
public class CacheFactory<I extends CacheProxy<String, Object>> implements CacheProxyFactory {

    private I cacheProxy;

    public CacheFactory(I cacheProxy) {
        this.cacheProxy = cacheProxy;
    }

    @Override
    public I getCacheClient() {
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
