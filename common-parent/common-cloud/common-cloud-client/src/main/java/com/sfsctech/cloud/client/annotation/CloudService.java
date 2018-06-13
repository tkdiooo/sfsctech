package com.sfsctech.cloud.client.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 注册服务的应用名称
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CloudService {

    @AliasFor("appName")
    String value() default "";

    @AliasFor("value")
    String appName() default "";
}
