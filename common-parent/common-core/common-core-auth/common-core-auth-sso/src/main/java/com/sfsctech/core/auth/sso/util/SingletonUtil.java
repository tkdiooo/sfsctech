package com.sfsctech.core.auth.sso.util;

import com.sfsctech.core.auth.sso.config.SSOConfig;
import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.spring.initialize.ApplicationInitialize;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.core.web.properties.WebsiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class VerifyUtil
 *
 * @author 张麒 2017-11-28.
 * @version Description:
 */
public class SingletonUtil {

    private static final Logger logger = LoggerFactory.getLogger(SingletonUtil.class);

    private static SSOProperties ssoProperties;

    public static SSOProperties getSSOProperties() {
        if (null == ssoProperties)
            synchronized (SSOProperties.class) {
                if (null == ssoProperties)
                    ssoProperties = SpringContextUtil.getBean(SSOProperties.class);
            }
        return ssoProperties;
    }

    private static CacheFactory<RedisProxy<String, Object>> factory;

    @SuppressWarnings("unchecked")
    public static CacheFactory<RedisProxy<String, Object>> getCacheFactory() {
        if (null == factory)
            synchronized (CacheFactory.class) {
                if (null == factory)
                    factory = SpringContextUtil.getBean(CacheFactory.class);
            }
        return factory;
    }

    private static SSOConfig authConfig;

    public static SSOConfig getAuthConfig() {
        if (null == authConfig)
            synchronized (SSOConfig.class) {
                if (null == authConfig)
                    authConfig = SpringContextUtil.getBean(SSOConfig.class);
            }
        return authConfig;
    }

    private static JwtProperties jwtProperties;

    public static JwtProperties getJwtProperties() {
        if (null == jwtProperties)
            synchronized (JwtProperties.class) {
                if (null == jwtProperties)
                    jwtProperties = SpringContextUtil.getBean(JwtProperties.class);
            }
        return jwtProperties;
    }

    private static ApplicationInitialize application;

    public static ApplicationInitialize getApplication() {
        if (null == application)
            synchronized (ApplicationInitialize.class) {
                if (null == application)
                    application = SpringContextUtil.getBean(ApplicationInitialize.class);
            }
        return application;
    }
}
