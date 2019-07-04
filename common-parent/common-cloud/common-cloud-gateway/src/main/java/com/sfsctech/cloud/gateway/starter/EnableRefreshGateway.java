package com.sfsctech.cloud.gateway.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * 开启代理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan("com.sfsctech.cloud.gateway.refresh")
public @interface EnableRefreshGateway {
}
