package com.sfsctech.mybatis.configurer;

import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.inf.dubbox.DubboxService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Class ServiceConfigurer
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
//@Configuration
public class ServiceConfigurer {

    @Resource
    private DubboxService dubboxService;

    @Bean
    public ServiceConfig<DubboxService> serviceConfig() {
        ServiceConfig<DubboxService> config = new ServiceBean<>();
        config.setInterface(DubboxService.class);
        config.setRef(dubboxService);
        return config;
    }
}
