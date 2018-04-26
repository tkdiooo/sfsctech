package com.sfsctech.authorize.sso.util;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sfsctech.authorize.base.inf.VerifyService;
import com.sfsctech.authorize.sso.properties.AuthConfig;
import com.sfsctech.authorize.base.properties.SSOProperties;
import com.sfsctech.cache.CacheFactory;
import com.sfsctech.cache.redis.inf.IRedisService;
import com.sfsctech.common.util.SpringContextUtil;
import com.sfsctech.spring.properties.WebsiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil {

    private static final Logger logger = LoggerFactory.getLogger(SingletonUtil.class);

    private static VerifyService verifyService;

    public static VerifyService getVerifyService() {
        if (null == verifyService)
            synchronized (VerifyService.class) {
                if (null == verifyService)
                    verifyService = (VerifyService) SpringContextUtil.getBean(ReferenceConfig.class).get();
            }
        return verifyService;
    }

    private static SSOProperties ssoProperties;

    public static SSOProperties getSSOProperties() {
        if (null == ssoProperties)
            synchronized (WebsiteProperties.class) {
                if (null == ssoProperties)
                    ssoProperties = SpringContextUtil.getBean(SSOProperties.class);
            }
        return ssoProperties;
    }

    private static CacheFactory<IRedisService<String, Object>> factory;

    @SuppressWarnings("unchecked")
    public static CacheFactory<IRedisService<String, Object>> getCacheFactory() {
        if (null == factory)
            synchronized (WebsiteProperties.class) {
                if (null == factory)
                    factory = SpringContextUtil.getBean(CacheFactory.class);
            }
        return factory;
    }

    private static AuthConfig authConfig;

    public static AuthConfig getAuthConfig() {
        if (null == authConfig)
            synchronized (AuthConfig.class) {
                if (null == authConfig)
                    authConfig = SpringContextUtil.getBean(AuthConfig.class);
            }
        return authConfig;
    }

    private static RestTemplate restTemplate;

    public static RestTemplate getRestTemplate() {
        if (null == restTemplate)
            synchronized (RestTemplate.class) {
                if (null == restTemplate)
                    restTemplate = SpringContextUtil.getBean(RestTemplate.class);
            }
        return restTemplate;
    }
}
