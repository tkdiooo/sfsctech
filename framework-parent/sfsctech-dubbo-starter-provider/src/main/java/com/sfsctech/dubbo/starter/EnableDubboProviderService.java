package com.sfsctech.dubbo.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.core.cache.config.CacheConfig;
import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.spring.config.SpringConfig;
import com.sfsctech.core.spring.config.TomcatConfig;
import com.sfsctech.dubbo.base.config.DubboxConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SpringConfig.class, CacheConfig.class, DubboxConfig.class, LogbackConfig.class, TomcatConfig.class})
@SpringBootApplication
public @interface EnableDubboProviderService {


}
