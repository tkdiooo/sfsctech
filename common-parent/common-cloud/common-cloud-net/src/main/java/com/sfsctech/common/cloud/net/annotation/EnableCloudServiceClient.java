package com.sfsctech.common.cloud.net.annotation;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.common.cloud.net.config.InterfaceProxyFactoryConfiguration;
import com.sfsctech.common.cloud.net.register.CloudServiceRegister;
import com.sfsctech.common.core.logger.config.LogbackConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启代理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({InterfaceProxyFactoryConfiguration.class, CloudServiceRegister.class, LogbackConfig.class})
//, LogConfiguration.class, TraceConfiguration.class})
public @interface EnableCloudServiceClient {

    String[] basePackages() default {};

}
