package com.sfsctech.demo.cloud.feign;

import com.sfsctech.cloud.net.starter.EnableCloudController;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Class Star
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@EnableCloudController(packages = {"com.sfsctech.demo.*.inf.service"})
public class CloudFeignStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudFeignStarter.class).web(WebApplicationType.SERVLET).run(args);
    }
}
