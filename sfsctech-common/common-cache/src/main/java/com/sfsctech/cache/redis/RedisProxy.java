package com.sfsctech.cache.redis;

import com.sfsctech.cache.condition.SingleProtocolCondition;
import com.sfsctech.cache.redis.inf.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Class RedisProxy
 *
 * @author 张麒 2017/6/15.
 * @version Description:
 */
@Component
@Conditional(SingleProtocolCondition.class)
public class RedisProxy implements IRedisService<String, Object> {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * @param key    key
     * @param expire expire
     * @return boolean
     */
    @Override
    public Object expire(String key, int expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Boolean put(String key, Object value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            connection.set(keySlz.serialize(key), valueSlz.serialize(value));
            return true;
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object get(String key) {
        return redisTemplate.execute((RedisCallback<Object>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            byte[] value = connection.get(keySlz.serialize(key));
            return valueSlz.deserialize(value);
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public long lpush(String key, Object value) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            return connection.lPush(keySlz.serialize(key), valueSlz.serialize(value));
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public long rpush(String key, Object value) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            return connection.rPush(keySlz.serialize(key), valueSlz.serialize(value));
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object lpop(String key) {
        return redisTemplate.execute((RedisCallback<Object>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            return valueSlz.deserialize(connection.lPop(keySlz.serialize(key)));
        });
    }

    @Override
    public Object add(String key, Object value) {
        return put(key, value);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object putTimeOut(String key, Object value, int timeOut) {
        return redisTemplate.execute((RedisCallback) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            RedisSerializer valueSlz = redisTemplate.getValueSerializer();
            connection.setEx(keySlz.serialize(key), timeOut, valueSlz.serialize(value));
            return null;
        });
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
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object remove(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer keySlz = redisTemplate.getKeySerializer();
            return connection.del(keySlz.serialize(key));
        });
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
