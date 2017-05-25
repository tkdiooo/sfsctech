package com.sfsctech.mybatis;

import com.sfsctech.common.dubbox.properties.DubboConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class MybatisService
 *
 * @author 张麒 2017/5/19.
 * @version Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sfsctech.mybatis", "com.sfsctech.common"})
public class MybatisService {

    public static void main(String[] args) throws Exception {
        DubboConfig.setAnnotationPackage("com.sfsctech.mybatis.service.provider");
        DubboConfig.setKryoSerializePackage("com.sfsctech.common.http");
        SpringApplication.run(MybatisService.class, args);
    }

}
