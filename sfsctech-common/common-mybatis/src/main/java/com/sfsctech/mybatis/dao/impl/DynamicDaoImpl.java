package com.sfsctech.mybatis.dao.impl;

import com.sfsctech.mybatis.dao.monitor.MyBatisDaoSupportMonitor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class DynamicDaoImpl
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
public abstract class DynamicDaoImpl extends MyBatisDaoSupportMonitor {

    @Autowired
    private void initSessionFactory(SqlSessionFactory sessionFactory) {
        super.setSqlSessionFactory(sessionFactory);
    }

}
