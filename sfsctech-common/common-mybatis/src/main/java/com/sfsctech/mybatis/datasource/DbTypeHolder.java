package com.sfsctech.mybatis.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class DbTypeHolder
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class DbTypeHolder {

    private static final Logger logger = LoggerFactory.getLogger(DbTypeHolder.class);
    private static final ThreadLocal<DBType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DBType dbType) {
        logger.debug("dbtype=" + dbType);
        contextHolder.set(dbType);
    }

    public static DBType getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
