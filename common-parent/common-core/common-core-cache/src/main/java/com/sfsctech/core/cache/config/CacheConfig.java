package com.sfsctech.core.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.properties.RedisProperties;
import com.sfsctech.core.cache.redis.RedisCacheClient;
import com.sfsctech.core.cache.redis.RedisProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Class CacheConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import(RedisProperties.class)
public class CacheConfig {

    private final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private RedisProperties properties;

    private RedisClusterConfiguration clusterConfiguration;

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
        return cacheManager;
    }

    @Bean
    public CacheFactory<RedisProxy<String, Object>> cacheProxyFactory() {
//        if (null != properties.getSingle()) {
        return new CacheFactory<>(new RedisCacheClient(redisTemplate()));
//        } else {
//            return new CacheFactory<>(new JedisCacheClient(jedisCluster(), new StringRedisSerializer(), jackson2JsonRedisSerializer()));
//        }
    }

    /**
     * Redis 单机
     *
     * @return RedisTemplate
     */
    @Bean
    @ConditionalOnMissingBean(name = {"redisTemplate"})
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({RedisConnectionFactory.class})
    public JedisConnectionFactory redisConnectionFactory() {
        return this.applyProperties(this.createJedisConnectionFactory());
    }

    private JedisConnectionFactory createJedisConnectionFactory() {
        JedisPoolConfig poolConfig = this.properties.getPool() != null ? this.jedisPoolConfig() : new JedisPoolConfig();
        if (properties.getCluster() != null) {
            return this.getClusterConfiguration() != null ? new JedisConnectionFactory(this.getClusterConfiguration(), poolConfig) : new JedisConnectionFactory(poolConfig);
        } else {
            return new JedisConnectionFactory(poolConfig);
        }
    }

    private JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
        factory.setHostName(this.properties.getHost());
        factory.setPort(this.properties.getPort());
        if (this.properties.getPassword() != null) {
            factory.setPassword(this.properties.getPassword());
        }
        if (this.properties.isSsl()) {
            factory.setUseSsl(true);
        }

        factory.setDatabase(this.properties.getDatabase());
        if (this.properties.getTimeout() > 0) {
            factory.setTimeout(this.properties.getTimeout());
        }

        return factory;
    }

    private RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        } else if (this.properties.getCluster() == null) {
            return null;
        } else {
            this.clusterConfiguration = new RedisClusterConfiguration(this.properties.getCluster().getNodes());
            if (this.properties.getCluster().getMaxRedirects() != null) {
                clusterConfiguration.setMaxRedirects(this.properties.getCluster().getMaxRedirects());
            }
            return clusterConfiguration;
        }
    }

    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(om);
        return valueSerializer;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getPool().getMaxActive());
        config.setMaxIdle(properties.getPool().getMaxIdle());
        config.setMaxWaitMillis(properties.getPool().getMaxWait());
        config.setMinIdle(properties.getPool().getMinIdle());
        return config;
    }
}
