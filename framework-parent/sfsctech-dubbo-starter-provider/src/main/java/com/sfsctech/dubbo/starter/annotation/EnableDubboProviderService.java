package com.sfsctech.dubbo.starter.annotation;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.core.logger.config.LogbackConfig;
import com.sfsctech.data.mybatis.config.MyBatisConfig;
import com.sfsctech.dubbo.base.config.DubboxConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MyBatisConfig.class, DubboxConfig.class, LogbackConfig.class})
@SpringBootApplication
@EnableTransactionManagement
public @interface EnableDubboProviderService {


}
