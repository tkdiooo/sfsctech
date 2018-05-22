package com.sfsctech.data.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.sfsctech.data.jdbc.model.DBConfigModel;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Class JDBCPool
 *
 * @author 张麒 2018-2-28.
 * @version Description:
 */
public class JdbcPool {

    private static Map<String, JdbcTemplate> JDBC_TEMPLATE = new HashMap<>();

    public static JdbcTemplate getJdbcTemplate(DBConfigModel configModel) {
        if (!JDBC_TEMPLATE.containsKey(configModel.getUrl())) {
            DruidDataSource ds = new DruidDataSource();
            ds.setDriverClassName(configModel.getDataSource().getDriver());
            ds.setUrl(configModel.getUrl());
            ds.setUsername(configModel.getUsername());
            ds.setPassword(configModel.getPassword());
            ds.setInitialSize(5);
            ds.setMaxActive(10);
            JDBC_TEMPLATE.put(configModel.getUrl(), new JdbcTemplate(ds));
        }
        return JDBC_TEMPLATE.get(configModel.getUrl());
    }
}
