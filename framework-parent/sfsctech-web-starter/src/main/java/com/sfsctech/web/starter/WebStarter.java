package com.sfsctech.web.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.exception.handler.GlobalExceptionHandler;
import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.web.config.WebConfig;
import com.sfsctech.core.rest.config.RestTemplateFactoryConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebConfig.class, RestTemplateFactoryConfig.class, GlobalExceptionHandler.class, CacheConfig.class, LogbackConfig.class})
@SpringBootApplication
public @interface WebStarter {

}
