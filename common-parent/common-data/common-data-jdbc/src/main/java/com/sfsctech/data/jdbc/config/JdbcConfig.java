package com.sfsctech.data.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Class JdbcConfig
 *
 * @author 张麒 2018-8-31.
 * @version Description:
 */
@Configuration
public class JdbcConfig {

    @Bean(name = "masterDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.druid-datasource.master")
    public DruidDataSource masterDatasource() {
        return new DruidDataSource();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DruidDataSource ds) {
        return new JdbcTemplate(ds);
    }
}
