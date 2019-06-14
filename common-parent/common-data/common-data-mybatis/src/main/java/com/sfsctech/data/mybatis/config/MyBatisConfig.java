package com.sfsctech.data.mybatis.config;

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.data.mybatis.datasource.ReadWriteDataSource;
import com.sfsctech.data.mybatis.datasource.aspect.DynamicDataSourceAspect;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MybatisConfigurer
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.*.*.mapper", "com.*.*.*.mapper"})
@Import({DynamicDataSourceAspect.class, CacheConfig.class})
@ConditionalOnClass(SqlSessionFactory.class)
public class MyBatisConfig {

    @Bean(name = "masterDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.hikari-datasource.master")
    public DataSource masterDatasource() {
        return new HikariDataSource();
    }

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties(prefix = "spring.hikari-datasource.slave")
    public Map<String, Map<String, HikariDataSource>> slaveDatasource() {
        return new HashMap<>();
    }

    @Bean(name = "dataSource")
    public DataSource dynamicDatasource() {
        ReadWriteDataSource dataSource = new ReadWriteDataSource();
        dataSource.setWriteDataSource(masterDatasource());
        dataSource.setReadDataSources(slaveDatasource());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDatasource());
        // 指定xml文件位置
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/config/mybatis/*/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDatasource());
    }

}
