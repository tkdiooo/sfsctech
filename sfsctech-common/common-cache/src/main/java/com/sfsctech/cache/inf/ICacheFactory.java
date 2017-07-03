package com.sfsctech.cache.inf;

/**
 * Class ICacheClient
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public interface ICacheFactory<K, V> {

    /**
     * 获取Cache客户端
     *
     * @return
     */
    ICacheService<K, V> getCacheClient();
}
