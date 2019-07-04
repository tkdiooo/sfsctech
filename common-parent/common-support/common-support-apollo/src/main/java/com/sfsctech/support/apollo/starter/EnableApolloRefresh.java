package com.sfsctech.support.apollo.starter;

/**
 * Class EnableCloudServiceClient
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */

import com.sfsctech.support.apollo.config.ApolloConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启代理
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ApolloConfig.class)
public @interface EnableApolloRefresh {

    String[] packages() default {};

}
