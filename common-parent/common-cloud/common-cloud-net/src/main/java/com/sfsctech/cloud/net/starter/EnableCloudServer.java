package com.sfsctech.cloud.net.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.cloud.net.config.InterfaceProxyFactoryConfiguration;
import com.sfsctech.cloud.net.register.CloudServiceRegister;
import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.exception.handler.GlobalExceptionHandler;
import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.web.config.WebConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启代理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({InterfaceProxyFactoryConfiguration.class, CloudServiceRegister.class, WebConfig.class, GlobalExceptionHandler.class, CacheConfig.class, LogbackConfig.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
public @interface EnableCloudServer {

    String[] packages() default {};

}
