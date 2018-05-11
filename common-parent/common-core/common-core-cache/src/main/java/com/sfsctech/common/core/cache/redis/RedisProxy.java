package com.sfsctech.common.core.cache.redis;

import com.sfsctech.common.core.cache.factory.CacheProxy;

/**
 * Class IRedisSerivce
 *
 * @author 张麒 2017/6/15.
 * @version Description:
 */
public interface RedisProxy<K, V> extends CacheProxy<K, V> {

    Object expire(K key, int expire);

    long lpush(K key, V obj);

    long rpush(K key, V obj);

    Object lpop(K key);

    long ttl(K key);
}
