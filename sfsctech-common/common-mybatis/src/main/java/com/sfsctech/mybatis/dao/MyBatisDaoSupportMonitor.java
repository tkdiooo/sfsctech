package com.sfsctech.mybatis.dao;

import com.sfsctech.cache.inf.ICacheClient;
import com.sfsctech.cache.inf.ICacheService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Class SqlMapClientDaoSupportMonitor
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public abstract class MyBatisDaoSupportMonitor {

    private MybatisCacheMonitor cacheMonitor = new MybatisCacheMonitor();

    protected final void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
        cacheMonitor.setSqlSessionFactory(sessionFactory);
    }

    protected final SqlSession getSqlSession() {
        return cacheMonitor.getSqlSession();
    }

    protected final void setCacheClient(ICacheClient<ICacheService<String, ?>> cacheClient) {
        this.cacheMonitor.setCacheClient(cacheClient);
    }
}
