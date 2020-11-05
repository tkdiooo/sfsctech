package com.sfsctech.core.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.properties.ExtraRedisProperties;
import com.sfsctech.core.cache.redis.RedisCacheClient;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.support.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;

/**
 * Class CacheConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Import(ExtraRedisProperties.class)
public class CacheConfig {

    private final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Autowired
    private RedisConnectionFactory factory;

    @Autowired
    private ExtraRedisProperties extraRedisProperties;

    private final ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers;

    CacheConfig(ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        this.builderCustomizers = builderCustomizers;
    }

    @Bean
    public CacheManager cacheManager() {
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))
                        .disableCachingNullValues()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()))
                ).build();
    }

    @Bean
    public CacheFactory<RedisProxy<String, Object>> cacheProxyFactory() {
        Map<String, RedisProxy<String, Object>> extraCacheProxy = new HashMap<>();
        if (extraRedisProperties.getExtra().size() > 0) {
            extraRedisProperties.getExtra().forEach((key, value) -> extraCacheProxy.put(key, new RedisCacheClient(extraRedisTemplate(value))));
        }
        return new CacheFactory<>(new RedisCacheClient(redisTemplate()), extraCacheProxy);
    }

    /**
     * @return RedisTemplate
     */
    @Bean
    @ConditionalOnMissingBean(name = {"redisTemplate"})
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valueSerializer.setObjectMapper(om);
        return valueSerializer;
    }

    private RedisTemplate<String, ?> extraRedisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(createConnectionFactory(redisProperties));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    private JedisConnectionFactory createConnectionFactory(RedisProperties redisProperties) {
        JedisClientConfiguration clientConfiguration = this.getJedisClientConfiguration(redisProperties);
        RedisSentinelConfiguration redisSentinelConfiguration = this.getSentinelConfig(redisProperties);
        if (null != redisSentinelConfiguration) {
            return new JedisConnectionFactory(redisSentinelConfiguration, clientConfiguration);
        } else {
            RedisClusterConfiguration redisClusterConfiguration = this.getClusterConfiguration(redisProperties);
            return null != redisClusterConfiguration ? new JedisConnectionFactory(redisClusterConfiguration, clientConfiguration) : new JedisConnectionFactory(this.getStandaloneConfig(redisProperties), clientConfiguration);
        }
    }

    private RedisStandaloneConfiguration getStandaloneConfig(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (StringUtils.hasText(redisProperties.getUrl())) {
            CacheConfig.ConnectionInfo connectionInfo = this.parseUrl(redisProperties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
        } else {
            config.setHostName(redisProperties.getHost());
            config.setPort(redisProperties.getPort());
            config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        config.setDatabase(redisProperties.getDatabase());
        return config;
    }

    private RedisSentinelConfiguration getSentinelConfig(RedisProperties redisProperties) {
        RedisProperties.Sentinel sentinelProperties = redisProperties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(this.createSentinels(sentinelProperties));
            if (StringUtil.isNotBlank(redisProperties.getPassword())) {
                config.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            config.setDatabase(redisProperties.getDatabase());
            return config;
        } else {
            return null;
        }
    }

    private RedisClusterConfiguration getClusterConfiguration(RedisProperties redisProperties) {
        if (redisProperties.getCluster() == null) {
            return null;
        } else {
            RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
            RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
            if (clusterProperties.getMaxRedirects() != null) {
                config.setMaxRedirects(clusterProperties.getMaxRedirects());
            }
            if (redisProperties.getPassword() != null) {
                config.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            return config;
        }
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        sentinel.getNodes().forEach(node -> {
            try {
                String[] parts = StringUtils.split(node, ":");
                assert parts != null;
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            } catch (RuntimeException var6) {
                throw new IllegalStateException("Invalid redis sentinel property '" + node + "'", var6);
            }
        });
        return nodes;
    }

    private CacheConfig.ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            boolean useSsl = url.startsWith("rediss://");
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(58);
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }

            return new CacheConfig.ConnectionInfo(uri, useSsl, password);
        } catch (URISyntaxException var6) {
            throw new IllegalArgumentException("Malformed url '" + url + "'", var6);
        }
    }

    private static class ConnectionInfo {
        private final URI uri;
        private final boolean useSsl;
        private final String password;

        ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        boolean isUseSsl() {
            return this.useSsl;
        }

        String getHostName() {
            return this.uri.getHost();
        }

        public int getPort() {
            return this.uri.getPort();
        }

        public String getPassword() {
            return this.password;
        }
    }

    private JedisClientConfiguration getJedisClientConfiguration(RedisProperties redisProperties) {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = this.applyProperties(JedisClientConfiguration.builder(), redisProperties);
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        if (pool != null) {
            this.applyPooling(pool, builder);
        }

        if (StringUtils.hasText(redisProperties.getUrl())) {
            this.customizeConfigurationFromUrl(builder, redisProperties);
        }

        this.customize(builder);
        return builder.build();
    }

    private JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(JedisClientConfiguration.JedisClientConfigurationBuilder builder, RedisProperties redisProperties) {
        if (redisProperties.isSsl()) {
            builder.useSsl();
        }

        if (redisProperties.getTimeout() != null) {
            Duration timeout = redisProperties.getTimeout();
            builder.readTimeout(timeout).connectTimeout(timeout);
        }
        return builder;
    }

    private void applyPooling(RedisProperties.Pool pool, JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        builder.usePooling().poolConfig(this.jedisPoolConfig(pool));
    }

    private JedisPoolConfig jedisPoolConfig(RedisProperties.Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getMaxWait() != null) {
            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return config;
    }

    private void customizeConfigurationFromUrl(JedisClientConfiguration.JedisClientConfigurationBuilder builder, RedisProperties redisProperties) {
        CacheConfig.ConnectionInfo connectionInfo = this.parseUrl(redisProperties.getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private void customize(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        this.builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    }
}
