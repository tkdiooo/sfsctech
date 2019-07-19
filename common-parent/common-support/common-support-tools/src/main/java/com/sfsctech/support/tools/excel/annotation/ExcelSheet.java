package com.sfsctech.support.tools.excel.annotation;

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

    int titleLine() default 0;
}
