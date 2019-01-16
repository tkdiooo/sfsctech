package com.sfsctech.data.jdbc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
    @ConfigurationProperties(prefix = "spring.hikari-datasource.master")
    public DataSource masterDatasource() {
        return new HikariDataSource();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
