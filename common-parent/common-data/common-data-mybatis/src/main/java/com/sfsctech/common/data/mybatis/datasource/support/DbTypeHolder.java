package com.sfsctech.common.data.mybatis.datasource.support;

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
    private static final ThreadLocal<DBType> DB_TYPE_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> DB_NAME_THREAD_LOCAL = new ThreadLocal<>();

    public static void setDbType(DBType dbType) {
        logger.debug("dbtype=" + dbType);
        DB_TYPE_THREAD_LOCAL.set(dbType);
    }

    public static void setDbName(String dbName) {
        logger.debug("dbtype=" + dbName);
        DB_NAME_THREAD_LOCAL.set(dbName);
    }

    public static DBType getDbType() {
        return DB_TYPE_THREAD_LOCAL.get();
    }

    public static String getDbName() {
        return DB_NAME_THREAD_LOCAL.get();
    }

    public static void clear() {
        DB_TYPE_THREAD_LOCAL.remove();
        DB_NAME_THREAD_LOCAL.remove();
    }
}
