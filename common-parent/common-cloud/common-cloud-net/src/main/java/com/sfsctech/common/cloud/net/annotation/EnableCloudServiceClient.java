package com.sfsctech.common.cloud.net.annotation;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.common.cloud.net.config.InterfaceProxyFactoryConfiguration;
import com.sfsctech.common.cloud.net.register.CloudServiceRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启代理
 */
// TODO 需要单独剥离出来，作为网站、服务项目统一使用
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({InterfaceProxyFactoryConfiguration.class, CloudServiceRegister.class})
public @interface EnableCloudServiceClient {

    String[] basePackages() default {};

}
