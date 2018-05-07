//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.annotation;

import com.bestv.common.net.config.InterfaceProxyFactoryConfiguration;
import com.bestv.common.net.config.LogConfiguration;
import com.bestv.common.net.config.TraceConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 接口代理开启
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({InterfaceProxyFactoryConfiguration.class, LogConfiguration.class, TraceConfiguration.class})
public @interface EnableCommonNetClient {
}
