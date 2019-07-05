package com.sfsctech.cloud.gateway.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.cloud.gateway.config.GatewayConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启代理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GatewayConfig.class)
public @interface EnableGatewayRefresh {
}
