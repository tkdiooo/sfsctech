package com.sfsctech.mybatis.annotation;

import com.sfsctech.mybatis.datasource.support.DBType;

import java.lang.annotation.*;

/**
 * Class ReadWriteDataSource
 *
 * @author 张麒 2017/6/28.
 * @version Description:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    DBType value() default DBType.WRITE;

    String name() default "";
}
