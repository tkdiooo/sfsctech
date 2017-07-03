package com.sfsctech.cache.redis.inf;

import com.sfsctech.cache.inf.ICacheService;

/**
 * Class IRedisSerivce
 *
 * @author 张麒 2017/6/15.
 * @version Description:
 */
public interface IRedisService<K, V> extends ICacheService<K, V> {

    boolean expire(K key, long expire);

    long lpush(K key, V obj);

    long rpush(K key, V obj);

    Object lpop(K key);
}
