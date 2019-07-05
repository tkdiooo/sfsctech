package com.sfsctech.cloud.gateway.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sfsctech.cloud.gateway.service.DynamicRouteService;
import com.sfsctech.core.spring.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class GatewayPropertiesRefresher
 *
 * @author 张麒 2019-6-27.
 * @version Description:
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class GatewayConfig implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);
    @Autowired
    private DynamicRouteService dynamicRouteService;
    private ApplicationContext applicationContext;

    @Bean
    public DynamicRouteService routeService() {
        return new DynamicRouteService();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * apollo配置监听器，默认监听的是application命名空间
     *
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean gatewayPropertiesChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            //前缀为spring.cloud.gateway的key发生了改变(gateway的配置发生了改变)
            if (changedKey.startsWith("spring.cloud.gateway.")) {
                gatewayPropertiesChanged = true;
                break;
            }
        }
        //更新gateway配置
        if (gatewayPropertiesChanged) {
            refreshGatewayProperties(changeEvent);
        }
    }

    /**
     * 更新SpringApplicationContext对象，并更新路由
     *
     * @param changeEvent
     */
    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {
        logger.info("Refreshing Gateway properties!");
        //更新配置
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        //更新路由
        SpringContextUtil.getBean(GatewayProperties.class).getRoutes().forEach(dynamicRouteService::update);
        logger.info("Gateway properties refreshed!");
    }

}
