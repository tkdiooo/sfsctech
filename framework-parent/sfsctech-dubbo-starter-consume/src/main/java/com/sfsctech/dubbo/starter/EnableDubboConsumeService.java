package com.sfsctech.dubbo.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.core.security.config.SecurityConfig;
import com.sfsctech.core.web.config.WebConfig;
import com.sfsctech.dubbo.base.config.DubboxConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebConfig.class, SecurityConfig.class, DubboxConfig.class, LogbackConfig.class})
@SpringBootApplication
public @interface EnableDubboConsumeService {


}
