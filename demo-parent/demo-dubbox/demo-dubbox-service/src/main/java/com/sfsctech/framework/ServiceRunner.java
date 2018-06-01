package com.sfsctech.framework;

import com.sfsctech.core.auth.sso.config.SSOConfig;
import com.sfsctech.dubbo.starter.annotation.EnableDubboProviderService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Class ServiceRunning
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
@EnableDubboProviderService
@Import(SSOConfig.class)
public class ServiceRunner {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRunner.class, args);
    }
}
