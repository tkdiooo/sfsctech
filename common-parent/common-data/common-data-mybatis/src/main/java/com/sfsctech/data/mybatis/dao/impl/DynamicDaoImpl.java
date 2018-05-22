package com.sfsctech.data.mybatis.dao.impl;

import com.sfsctech.core.cache.factory.CacheProxy;
import com.sfsctech.core.cache.factory.CacheProxyFactory;
import com.sfsctech.data.mybatis.dao.monitor.MyBatisDaoSupportMonitor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Class DynamicDaoImpl
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
abstract class DynamicDaoImpl<T, PK extends Serializable, Example> extends MyBatisDaoSupportMonitor<T, PK, Example> {

    @Autowired
    private void initSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

    @Autowired(required = false)
    private void initCacheFactory(CacheProxyFactory<CacheProxy<String, Object>> cacheFactory) {
        super.setCacheFactory(cacheFactory);
    }
}
