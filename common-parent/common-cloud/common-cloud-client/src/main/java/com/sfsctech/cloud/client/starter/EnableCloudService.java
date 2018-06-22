package com.sfsctech.cloud.client.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.exception.controller.GlobalErrorController;
import com.sfsctech.core.exception.controller.GlobalExceptionHandler;
import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.spring.config.SpringConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SpringConfig.class, GlobalErrorController.class, GlobalExceptionHandler.class, CacheConfig.class, LogbackConfig.class})
@SpringBootApplication
@EnableDiscoveryClient
public @interface EnableCloudService {

}
