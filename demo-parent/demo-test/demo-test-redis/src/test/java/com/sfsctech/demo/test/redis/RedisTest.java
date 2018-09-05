package com.sfsctech.demo.test.redis;

import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.demo.test.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class RedisTest
 *
 * @author 张麒 2018-8-13.
 * @version Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Runner.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class RedisTest {

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Autowired
    private RedisService service;


    @Test
    public void testRedis() {
        for (int i = 0; i < 300; i++) {
            factory.getCacheClient().putTimeOut("test" + i, "abc" + i, CacheConstants.MilliSecond.Minutes30.getContent());
        }

        for (int i = 0; i < 300; i++) {
            System.out.println(factory.get("test" + i).toString());
        }


    }
}
