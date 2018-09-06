package com.sfsctech.demo.test.redis;

import com.sfsctech.core.auth.sso.util.CacheKeyUtil;
import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.demo.test.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            keys.add(CacheKeyUtil.getSaltCacheKey());
        }

        keys.forEach(key -> factory.getCacheClient().putTimeOut(key, "abc:" + key, CacheConstants.MilliSecond.Minutes30.getContent()));

        keys.forEach(key -> System.out.println(factory.get(key).toString()));



    }
}
