package com.sfsctech.support.apollo.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Class ApolloConfig
 *
 * @author 张麒 2019-7-4.
 * @version Description:
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class ApolloConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApolloConfig.class);

    private final RefreshScope refreshScope;


    public ApolloConfig(final RefreshScope refreshScope) {
        this.refreshScope = refreshScope;
        logger.info("Apollo配置中心基于@RefreshScope注解的动态刷新功能启动");
    }

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("Apollo配置项发生变更,变更的key如下：");
        // 发生改变的Apollo配置信息
        changeEvent.changedKeys().forEach(key -> logger.info("changed key:" + key));
        // 刷新refreshScope
        logger.info("刷新所有标记@RefreshScope注解的配置类");
        refreshScope.refreshAll();
    }

}
