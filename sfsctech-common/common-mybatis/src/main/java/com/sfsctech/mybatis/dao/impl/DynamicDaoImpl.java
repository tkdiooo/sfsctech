package com.sfsctech.mybatis.dao.impl;

import com.sfsctech.cache.CacheFactory;
import com.sfsctech.mybatis.dao.monitor.MyBatisDaoSupportMonitor;
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

    @Autowired
    private void initCacheFactory(CacheFactory cacheFactory) {
        super.setCacheClient(cacheFactory);
    }
}
