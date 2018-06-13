package com.sfsctech.turbine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Class ConfigService
 *
 * @author 张麒 2017/5/17.
 * @version Description:
 */
@SpringBootApplication
@EnableTurbine
public class TurbineService {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TurbineService.class).web(true).run(args);
    }

}
