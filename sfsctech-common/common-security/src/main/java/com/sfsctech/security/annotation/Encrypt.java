package com.sfsctech.security.annotation;

import com.sfsctech.common.security.EncrypterTool.Security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class Encrypt
 *
 * @author 张麒 2017/7/31.
 * @version Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {

    Security value() default Security.Des3;
}
