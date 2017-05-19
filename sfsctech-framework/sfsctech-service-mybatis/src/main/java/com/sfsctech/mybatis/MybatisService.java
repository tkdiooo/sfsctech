package com.sfsctech.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        SpringApplication.run(MybatisService.class, args);
    }

}
