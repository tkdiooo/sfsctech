package com.sfsctech.demo.cloud.feign.service;

import com.sfsctech.demo.cloud.feign.service.impl.IndexServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class IndexService
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@FeignClient(value = "cloud-client",fallback = IndexServiceImpl.class)
public interface IndexService {

    @GetMapping("index")
    String index(@RequestParam("name") String name);


}
