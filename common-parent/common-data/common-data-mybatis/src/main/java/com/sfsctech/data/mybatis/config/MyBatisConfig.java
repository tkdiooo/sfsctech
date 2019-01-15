package com.sfsctech.data.mybatis.config;

import com.sfsctech.data.mybatis.datasource.ReadWriteDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Class MybatisConfigurer
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
public abstract class MyBatisConfig {

    protected abstract DataSource masterDatasource();

    protected abstract Map<String, Map<String, DataSource>> slaveDatasource();

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
