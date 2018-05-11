package com.sfsctech.common.support.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class ExcelSheet
 *
 * @author 张麒 2016/5/5.
 * @version Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheet {

    String name();

    int rower() default 0;
}
