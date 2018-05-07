//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RedisCacheClient extends AbstractCacheClient {
    private static final String SECOND_MARK = "EX";
    private static final String SET_MODE = "NX";
    private JedisPool jedisPool;
    private Jedis jedis;

    public RedisCacheClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public RedisCacheClient(Jedis jedis) {
        this.jedis = jedis;
    }

    public void set(String namespace, String key, Object value, int expire) {
        Pipeline pipeline = this.getRedisClient().pipelined();
        pipeline.del(this.generateKey(namespace, key));
        pipeline.set(this.generateKey(namespace, key), this.normalizeInputCacheValue(this.objectToString(value)), "NX", "EX", expire);
        pipeline.sync();
    }

    public void set(String namespace, String key, Object value) {
        Pipeline pipeline = this.getRedisClient().pipelined();
        pipeline.del(this.generateKey(namespace, key));
        pipeline.set(this.generateKey(namespace, key), this.normalizeInputCacheValue(this.objectToString(value)));
        pipeline.sync();
    }

    public void set(String namespace, Map<String, Object> cacheMap) {
        if (!StringUtils.isBlank(namespace) && !MapUtils.isEmpty(cacheMap)) {
            Pipeline pipeline = this.getRedisClient().pipelined();
            Iterator var4 = cacheMap.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<String, Object> entry = (Entry)var4.next();
                pipeline.del(this.generateKey(namespace, (String)entry.getKey()));
                pipeline.set(this.generateKey(namespace, (String)entry.getKey()), this.normalizeInputCacheValue(this.objectToString(entry.getValue())));
            }

            pipeline.sync();
        }
    }

    public void set(String namespace, Map<String, Object> cacheMap, int expire) {
        if (!StringUtils.isBlank(namespace) && !MapUtils.isEmpty(cacheMap)) {
            Pipeline pipeline = this.getRedisClient().pipelined();
            Iterator var5 = cacheMap.entrySet().iterator();

            while(var5.hasNext()) {
                Entry<String, Object> entry = (Entry)var5.next();
                pipeline.del(this.generateKey(namespace, (String)entry.getKey()));
                pipeline.set(this.generateKey(namespace, (String)entry.getKey()), this.normalizeInputCacheValue(this.objectToString(entry.getValue())), "NX", "EX", expire);
            }

            pipeline.sync();
        }
    }

    public void incr(String namespace, String key) {
        this.getRedisClient().incr(this.generateKey(namespace, key));
    }

    public void decr(String namespace, String key) {
        this.getRedisClient().decr(this.generateKey(namespace, key));
    }

    public void del(String namespace, String key) {
        this.getRedisClient().del(this.generateKey(namespace, key));
    }

    public void del(String namespace, List<String> keys) {
        if (!StringUtils.isBlank(namespace) && !CollectionUtils.isEmpty(keys)) {
            Pipeline pipeline = this.getRedisClient().pipelined();
            Iterator var4 = keys.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                pipeline.del(this.generateKey(namespace, key));
            }

            pipeline.sync();
        }
    }

    public boolean exists(String namespace, String key) {
        return this.getRedisClient().exists(this.generateKey(namespace, key));
    }

    public Map<String, Boolean> exists(String namespace, List<String> keys) {
        if (!StringUtils.isBlank(namespace) && !CollectionUtils.isEmpty(keys)) {
            Map<String, Boolean> result = new HashMap();
            Map<String, Response<Boolean>> responseMap = new HashMap();
            Pipeline pipeline = this.getRedisClient().pipelined();
            Iterator var6 = keys.iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                Response<Boolean> existsResponse = pipeline.exists(this.generateKey(namespace, key));
                responseMap.put(key, existsResponse);
            }

            pipeline.sync();
            var6 = responseMap.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, Response<Boolean>> responseEntry = (Entry)var6.next();
                result.put(responseEntry.getKey(), (Boolean) ((Response)responseEntry.getValue()).get());
            }

            return result;
        } else {
            return new HashMap();
        }
    }

    public String get(String namespace, String key) {
        if (!StringUtils.isBlank(namespace) && !StringUtils.isBlank(key)) {
            String cacheValue = this.getRedisClient().get(this.generateKey(namespace, key));
            cacheValue = this.normalizeOutputCacheValue(cacheValue);
            return cacheValue;
        } else {
            return null;
        }
    }

    public Map<String, String> get(String namespace, List<String> keys) {
        if (!StringUtils.isBlank(namespace) && !CollectionUtils.isEmpty(keys)) {
            Map<String, String> result = new HashMap();
            Map<String, Response<String>> responseMap = new HashMap();
            Pipeline pipeline = this.getRedisClient().pipelined();
            Iterator var6 = keys.iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                Response<String> response = pipeline.get(this.generateKey(namespace, key));
                responseMap.put(key, response);
            }

            pipeline.sync();
            var6 = responseMap.entrySet().iterator();

            while(var6.hasNext()) {
                Entry<String, Response<String>> responseEntry = (Entry)var6.next();
                String cacheKey = (String)responseEntry.getKey();
                String cacheValue = (String)((Response)responseEntry.getValue()).get();
                cacheValue = this.normalizeOutputCacheValue(cacheValue);
                result.put(cacheKey, cacheValue);
            }

            return result;
        } else {
            return null;
        }
    }

    private String normalizeInputCacheValue(String cacheValue) {
        return cacheValue;
    }

    private String normalizeOutputCacheValue(String cacheValue) {
        return cacheValue;
    }

    private Jedis getRedisClient() {
        return this.jedisPool == null ? this.jedis : this.jedisPool.getResource();
    }
}
