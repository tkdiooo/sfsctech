package com.sfsctech.common.core.cache.redis;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;

/**
 * Class JedisProxy
 *
 * @author 张麒 2017-11-3.
 * @version Description:
 */
public class JedisCacheClient implements RedisProxy<String, Object> {

    private JedisCluster jedisCluster;

    private StringRedisSerializer keySerializer;

    private Jackson2JsonRedisSerializer<Object> valSerializer;

    public JedisCacheClient(JedisCluster jedisCluster, StringRedisSerializer keySerializer, Jackson2JsonRedisSerializer<Object> valSerializer) {
        this.jedisCluster = jedisCluster;
        this.keySerializer = keySerializer;
        this.valSerializer = valSerializer;
    }

    /**
     * @param key    key
     * @param expire expire
     * @return Long
     */
    @Override
    public Object expire(String key, int expire) {
        return jedisCluster.expire(key, expire);
    }

    @Override
    public long lpush(String key, Object value) {
        return jedisCluster.lpush(keySerializer.serialize(key), valSerializer.serialize(value));
    }

    @Override
    public long rpush(String key, Object value) {
        return jedisCluster.rpush(keySerializer.serialize(key), valSerializer.serialize(value));
    }

    @Override
    public Object lpop(String key) {
        return valSerializer.deserialize(jedisCluster.lpop(keySerializer.serialize(key)));
    }

    @Override
    public long ttl(String key) {
        return jedisCluster.ttl(keySerializer.serialize(key));
    }

    @Override
    public Object put(String key, Object value) {
        return jedisCluster.set(keySerializer.serialize(key), valSerializer.serialize(value));
    }

    @Override
    public Object add(String key, Object value) {
        return put(key, value);
    }

    @Override
    public Object putTimeOut(String key, Object value, int timeOut) {
        return jedisCluster.setex(keySerializer.serialize(key), timeOut, valSerializer.serialize(value));
    }

    @Override
    @Deprecated
    public Object add(String key, Object value, Integer TTL) {
        return null;
    }

    @Override
    @Deprecated
    public Object addTimeOut(String key, Object value, int timeout) {
        return null;
    }

    @Override
    public Object get(String key) {
        return valSerializer.deserialize(jedisCluster.get(keySerializer.serialize(key)));
    }

    @Override
    public Object remove(String key) {
        return jedisCluster.del(keySerializer.serialize(key));
    }

    @Override
    @Deprecated
    public Object replace(String key, Object value) {
        return null;
    }

    @Override
    @Deprecated
    public boolean flushAll() {
        return false;
    }
}
