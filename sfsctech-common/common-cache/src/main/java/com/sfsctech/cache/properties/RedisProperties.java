package com.sfsctech.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class CacheProperties
 *
 * @author 张麒 2017-11-2.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "spring.redis"
)
public class RedisProperties {

    private String protocol;

    private final RedisProperties.Pool pool;
    private final RedisProperties.Single single;
    private final RedisProperties.Cluster cluster;

    public RedisProperties() {
        this.pool = new RedisProperties.Pool();
        this.single = new RedisProperties.Single();
        this.cluster = new RedisProperties.Cluster();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Pool getPool() {
        return pool;
    }

    public Single getSingle() {
        return single;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public static class Pool {
        private int maxIdle = 8;
        private int minIdle = 0;
        private int maxActive = 8;
        private int maxWait = -1;

        Pool() {
        }

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }
    }

    public static class Single {
        private int database = 0;
        private String host = "localhost";
        private String password;
        private int port = 6379;
        private int timeout;

        Single() {
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }


    public static class Cluster {
        private List<String> nodes;
        private Integer maxRedirects = 5;
        private Integer connectionTimeout;
        private Integer soTimeout;
        private Integer maxAttempts;
        private String password;

        Cluster() {
        }

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }

        public Integer getMaxRedirects() {
            return this.maxRedirects;
        }

        public void setMaxRedirects(Integer maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

        public Integer getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public Integer getSoTimeout() {
            return soTimeout;
        }

        public void setSoTimeout(Integer soTimeout) {
            this.soTimeout = soTimeout;
        }

        public Integer getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
