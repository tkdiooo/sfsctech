package com.sfsctech.core.cache.factory;

/**
 * Class ICacheClient
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public interface CacheProxyFactory<T extends CacheProxy<String, Object>> {

    /**
     * 获取Cache客户端
     */
    T getCacheClient();
}
