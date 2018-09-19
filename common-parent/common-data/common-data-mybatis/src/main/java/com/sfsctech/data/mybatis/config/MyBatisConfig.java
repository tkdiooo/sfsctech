package com.sfsctech.data.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.data.mybatis.datasource.ReadWriteDataSource;
import com.sfsctech.data.mybatis.datasource.aspect.DynamicDataSourceAspect;
import com.sfsctech.support.common.util.HttpUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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
@ConditionalOnClass(org.apache.ibatis.session.SqlSessionFactory.class)
public class MyBatisConfig {

    @Bean(name = "masterDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.druid-datasource.master")
    public DruidDataSource masterDatasource() {
        return new DruidDataSource();
    }

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties(prefix = "spring.druid-datasource.slave")
    public Map<String, Map<String, DruidDataSource>> slaveDatasource() {
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

    /**
     * druid注册一个StatViewServlet
     *
     * @return ServletRegistrationBean
     */
    // TODO 需要添加配置
    @Bean
    public ServletRegistrationBean DruidStatViewServlet() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", HttpUtil.getServerIp());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        servletRegistrationBean.addInitParameter("deny", HttpUtil.getServerIp());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 注册WebStatFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean druidWebStatFilter = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        druidWebStatFilter.addUrlPatterns(LabelConstants.SLASH_STAR);
        //添加不需要忽略的格式信息.
        druidWebStatFilter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return druidWebStatFilter;
    }

    // 按照BeanId来拦截配置 用来bean的监控
    @Bean(value = "druid-stat-interceptor")
    @ConditionalOnProperty(name = "spring.druid-datasource.spring-monitor.enabled", havingValue = "true")
    public DruidStatInterceptor DruidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    @ConditionalOnBean(DruidStatInterceptor.class)
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setProxyTargetClass(true);
        // 设置要监控的bean的id
        beanNameAutoProxyCreator.setBeanNames("*ServiceImpl");
        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
        return beanNameAutoProxyCreator;
    }
}
