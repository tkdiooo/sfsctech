package com.sfsctech.configurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfsctech.cache.condition.CacheCondition;
import com.sfsctech.cache.condition.ClusterProtocolCondition;
import com.sfsctech.cache.condition.SingleProtocolCondition;
import com.sfsctech.constants.LabelConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Class RedisConfigurer
 *
 * @author 张麒 2017/6/15.
 * @version Description:
 */
@Configuration
@Conditional(CacheCondition.class)
@ComponentScan(basePackages = "com.sfsctech.cache")
public class CacheConfigurer {

    private final Logger logger = LoggerFactory.getLogger(CacheConfigurer.class);

    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(properties.getPool().getMaxActive());
        config.setMaxIdle(properties.getPool().getMaxIdle());
        config.setMaxWaitMillis(properties.getPool().getMaxWait());
        config.setMinIdle(properties.getPool().getMinIdle());
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);
        factory.setDatabase(properties.getDatabase());
        factory.setHostName(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setPassword(properties.getPassword());
        factory.setTimeout(properties.getTimeout());
        return factory;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
        return cacheManager;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(om);
        return valueSerializer;
    }

    @Bean
    @Conditional(SingleProtocolCondition.class)
    public RedisTemplate<String, ?> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * Redis 集群设置
     *
     * @return
     */
    @Bean
    @Conditional(ClusterProtocolCondition.class)
    public JedisCluster jedisCluster(JedisPoolConfig jedisPoolConfig) {
        JedisPool pool = new JedisPool();
        Set<HostAndPort> nodes = new HashSet<>();
        properties.getCluster().getNodes().forEach(node -> {
            String[] ipPortPair = node.split(LabelConstants.COLON);
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        });
        // 连接超时
        // 读取数据超时
        // 出现异常最大重试次数
        return new JedisCluster(nodes, 10000, 1000, 3, "redis123", jedisPoolConfig);
    }
}
