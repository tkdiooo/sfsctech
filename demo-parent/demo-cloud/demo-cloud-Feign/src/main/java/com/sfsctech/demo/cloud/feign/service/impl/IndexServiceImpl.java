package com.sfsctech.demo.cloud.feign.service.impl;

import com.sfsctech.demo.cloud.feign.service.IndexService;
import org.springframework.stereotype.Component;

/**
 * Class SchedualServiceHiHystric
 *
 * @author 张麒 2018-4-28.
 * @version Description:
 */
@Component
public class IndexServiceImpl implements IndexService {

    @Override
    public String index(String name) {
        return "sorry " + name;
    }
}
