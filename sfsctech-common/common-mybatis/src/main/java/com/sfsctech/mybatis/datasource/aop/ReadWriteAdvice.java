package com.sfsctech.mybatis.datasource.aop;

import com.sfsctech.mybatis.annotation.DataSource;
import com.sfsctech.mybatis.datasource.support.DbTypeHolder;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Spring aop method 前置通知
 * Class ReadWriteAdvice
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
public class ReadWriteAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
        //判断该类是否加了注解
        if (arg2.getClass().isAnnotationPresent(DataSource.class)) {
            DataSource rwd = arg2.getClass().getAnnotation(DataSource.class);
            DbTypeHolder.setDbType(rwd.value());
        }
        // 判断该方法是否加了注解
        if (arg0.isAnnotationPresent(DataSource.class)) {
            DataSource rwd = arg0.getAnnotation(DataSource.class);
            DbTypeHolder.setDbType(rwd.value());
        }
    }
}
