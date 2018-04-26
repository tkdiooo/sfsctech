package com.sfsctech.cache.inf;

/**
 * Class ICacheClient
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public interface ICacheFactory<T extends ICacheService> {

    /**
     * 获取Cache客户端
     *
     * @return
     */
    T getCacheClient();
}
