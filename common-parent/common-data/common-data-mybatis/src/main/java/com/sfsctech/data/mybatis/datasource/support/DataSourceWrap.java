package com.sfsctech.data.mybatis.datasource.support;

import javax.sql.DataSource;

/**
 * Class DataSourceWrap
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class DataSourceWrap {

    public DataSourceWrap(Object key, DataSource dataSource) {
        super();
        this.key = key;
        this.dataSource = dataSource;
    }

    private Object key;

    private DataSource dataSource;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
