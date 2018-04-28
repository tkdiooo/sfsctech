package com.sfsctech.demo.cloud.hystrix.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sfsctech.demo.cloud.hystrix.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Class IndexServiceImpl
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @HystrixCommand(fallbackMethod = "hiError")
    public String index(String name) {
        return restTemplate.getForObject("http://CLOUD-CLIENT/index?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }
}
