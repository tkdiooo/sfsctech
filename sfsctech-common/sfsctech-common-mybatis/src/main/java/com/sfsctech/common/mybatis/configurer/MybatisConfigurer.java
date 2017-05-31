package com.sfsctech.common.mybatis.configurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class MybatisConfigurer
 *
 * @author 张麒 2017/5/25.
 * @version Description:
 */
@Configuration
@MapperScan("com.sfsctech.*.mapper")
public class MybatisConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource() throws Exception {
        return new DruidDataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasource());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/config/ibatis/*/*.xml"));//指定xml文件位置
        return factoryBean.getObject();
    }
}
