package com.sfsctech.mybatis.datasource;

import javax.sql.DataSource;

/**
 * Class DataSourceWrap
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class DataSourceWrap {

    public DataSourceWrap(Object key, javax.sql.DataSource dataSource) {
        super();
        this.key = key;
        this.dataSource = dataSource;
    }

    private Object key;

    private javax.sql.DataSource dataSource;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public javax.sql.DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
