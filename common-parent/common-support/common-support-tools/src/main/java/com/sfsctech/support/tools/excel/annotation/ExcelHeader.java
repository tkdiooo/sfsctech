package com.sfsctech.support.tools.excel.annotation;

import com.sfsctech.support.tools.excel.inf.ComplexDataMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class ExcelHeader
 *
 * @author 张麒 2016/5/5.
 * @version Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {

    String value();

    Class<? extends ComplexDataMapping> complexMapping() default ExcelHeader.None.class;


    abstract class None implements ComplexDataMapping {

        private None() {
        }
    }
}
