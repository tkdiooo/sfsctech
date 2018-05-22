package com.sfsctech.data.mybatis.datasource.support;

import com.sfsctech.data.mybatis.consistenthash.Node;
import com.sfsctech.data.mybatis.consistenthash.RoundRobinWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class RecoveryDataSourceThread
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class RecoveryDataSourceThread implements Runnable {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static int SLEEP_TIME = 1000 * 60; // 一分钟
    private static final int CONNECTION_VALID_TIME = 3;

    /**
     * 检查数据源是否可用的语句,默认是select 1 。 oracle下请修改为 select 1 from dual
     */
    private String checkAvailableSql = "select 1";

    private DataSourceWrap dsw = null;

    public RecoveryDataSourceThread(DataSourceWrap dsw) {
        this.dsw = dsw;
    }

    public RecoveryDataSourceThread(Object dataSourceKey, DataSource dataSource) {
        this.dsw = new DataSourceWrap(dataSourceKey, dataSource);
    }

    @Override
    public void run() {
        while (true) {
            if (dsw == null) {
                return;
            }
            Object dataSourceKey = dsw.getKey();
            DataSource dataSource = dsw.getDataSource();
            if (dataSourceKey != null && dataSource != null) {
                if (isDataSourceAvailable(dataSource)) {
                    RoundRobinWeight.addNode(new Node(dataSourceKey.toString()));
                    logger.info("Recovery DataSource to available ");
                    break;
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 检查连接是否可用, 必须吃掉所有异常。不准抛异常
     */
    private boolean isDataSourceAvailable(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if (conn.isValid(CONNECTION_VALID_TIME)) {
                Statement stmt = conn.createStatement();
                boolean success = stmt.execute(checkAvailableSql); // 如果执行成功，会返回结果
                stmt.close();
                return success;
            }
            return false;
        } catch (SQLException e) {
            logger.error("CheckDataSourceAvailable Exception", e);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Close Connection Exception", e);
                }
            }
        }
    }
}
