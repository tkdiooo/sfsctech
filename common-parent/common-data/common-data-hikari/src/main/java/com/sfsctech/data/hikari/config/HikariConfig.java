package com.sfsctech.data.hikari.config;

//import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.data.mybatis.config.MyBatisConfig;
import com.sfsctech.data.mybatis.datasource.aspect.DynamicDataSourceAspect;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Class DruidConfig
 *
 * @author 张麒 2019-1-14.
 * @version Description:
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.*.*.mapper", "com.*.*.*.mapper"})
@Import({DynamicDataSourceAspect.class, CacheConfig.class})
@ConditionalOnClass(SqlSessionFactory.class)
public class HikariConfig extends MyBatisConfig {

    @Bean(name = "masterDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.hikari-datasource.master")
    public DataSource masterDatasource() {
        return new HikariDataSource();
    }

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties(prefix = "spring.hikari-datasource.slave")
    public Map<String, Map<String, DataSource>> slaveDatasource() {
        return new HashMap<>();
    }

}
