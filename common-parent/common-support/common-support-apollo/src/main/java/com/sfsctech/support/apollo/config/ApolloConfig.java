package com.sfsctech.support.apollo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

    @Value("${app.id}")
    private String appId;

    @Value("${apollo.bootstrap.namespaces}")
    private String[] namespaces;

    public ApolloConfig(final RefreshScope refreshScope) {
        this.refreshScope = refreshScope;
        logger.info("Apollo配置中心基于@RefreshScope注解的动态刷新功能启动");
    }

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("Apollo AppId[{}] namespace[application] 配置项发生变更,变更的key如下：", appId);
        // 发生改变的Apollo配置信息
        changeEvent.changedKeys().forEach(key -> logger.info("changed key：{}", key));
        // 刷新refreshScope
        logger.info("刷新所有标记@RefreshScope注解的配置类");
        refreshScope.refreshAll();
    }

    @PostConstruct
    public void addRefreshListener() {
        for (String namespace : namespaces) {
            Config config = ConfigService.getConfig(namespace);
            //对namespace增加监听方法
            config.addChangeListener(changeEvent -> {
                logger.info("Apollo AppId[{}] namespace[{}] 配置项发生变更,变更的key如下：", appId, namespace);
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange configChange = changeEvent.getChange(key);
                    logger.info("changed key={}, oldValue={}, newValue={}", key, configChange.getOldValue(), configChange.getNewValue());
                }
                logger.info("刷新所有标记@RefreshScope注解的配置类");
                refreshScope.refreshAll();
                //将变动的配置刷新到应用中
//                SpringContextUtil.getApplicationContext().publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
            });
        }
    }

}
