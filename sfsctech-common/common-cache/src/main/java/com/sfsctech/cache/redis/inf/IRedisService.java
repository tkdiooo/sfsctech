package com.sfsctech.cache.redis.inf;

import com.sfsctech.cache.inf.ICacheService;

/**
 * Class IRedisSerivce
 *
 * @author 张麒 2017/6/15.
 * @version Description:
 */
public interface IRedisService extends ICacheService<String, Object> {

    boolean expire(String key, long expire);

    long lpush(String key, Object obj);

    long rpush(String key, Object obj);

    Object lpop(String key);
}
