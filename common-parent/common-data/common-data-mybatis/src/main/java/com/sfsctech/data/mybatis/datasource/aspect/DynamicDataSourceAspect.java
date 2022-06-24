package com.sfsctech.data.mybatis.datasource.aspect;

import com.sfsctech.data.mybatis.annotation.DataSource;
import com.sfsctech.data.mybatis.datasource.support.DbTypeHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Class DynamicDataSourceAspect
 *
 * @author 张麒 2018-4-17.
 * @version Description:
 */
@Aspect
@Order(value = 1)
@Component
public class DynamicDataSourceAspect {

    // 有注解DataSource的类或者方法
    @Pointcut("@within(com.sfsctech.data.mybatis.annotation.DataSource) || @annotation(com.sfsctech.data.mybatis.annotation.DataSource)")
    @AdviceName("")
    public void dynamicDataSourceAspect() {
    }

    @Before("dynamicDataSourceAspect()")
    public void beforeSwitchDataSource(JoinPoint point) {

        //获得当前访问的class
        Class<?> cls = point.getTarget().getClass();

        //判断该类是否加了注解
        if (cls.isAnnotationPresent(DataSource.class)) {
            DataSource rwd = cls.getAnnotation(DataSource.class);
            DbTypeHolder.setDbType(rwd.value());
            DbTypeHolder.setDbName(rwd.name());
        } else {
            //获得访问的方法名
            String methodName = point.getSignature().getName();
            //得到方法的参数的类型
            Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
            try {
                // 得到访问的方法对象
                Method method = cls.getMethod(methodName, argClass);

                // 判断是否存在@DS注解
                if (method.isAnnotationPresent(DataSource.class)) {
                    DataSource rwd = method.getAnnotation(DataSource.class);
                    // 取出注解中的数据源名
                    DbTypeHolder.setDbType(rwd.value());
                    DbTypeHolder.setDbName(rwd.name());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @After("dynamicDataSourceAspect()")
    public void afterSwitchDataSource(JoinPoint point) {
        DbTypeHolder.clear();
    }
}
