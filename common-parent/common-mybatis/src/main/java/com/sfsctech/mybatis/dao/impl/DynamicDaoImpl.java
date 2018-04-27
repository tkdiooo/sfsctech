package com.sfsctech.mybatis.dao.impl;

import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.mybatis.dao.monitor.MyBatisDaoSupportMonitor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
    private void initCacheFactory(ICacheFactory<ICacheService<String, Object>> cacheFactory) {
        super.setCacheFactory(cacheFactory);
    }
}
