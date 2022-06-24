package com.sfsctech.demo.test.redis.service.impl;

import com.sfsctech.demo.test.redis.service.RedisService;
import org.springframework.stereotype.Service;

/**
 * Class RedisServiceImpl
 *
 * @author 张麒 2018-8-13.
 * @version Description:
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Override
    public void test() {
        System.out.println("test");
    }
}
