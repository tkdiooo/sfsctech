package com.sfsctech.configurer;

import com.sfsctech.spring.condition.TomcatCondition;
import com.sfsctech.spring.properties.AppConfig;
import com.sfsctech.spring.properties.TomcatProperties;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Class TomcatConfigurer
 *
 * @author 张麒 2017/9/14.
 * @version Description:
 */
@Configuration
@Conditional(TomcatCondition.class)
@ComponentScan(basePackageClasses = AppConfig.class)
public class TomcatConfigurer {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private TomcatProperties properties;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setProtocol(properties.getConnector());
        TomcatConnectorCustomizer connectorCustomizer = connector -> {
            if (properties.getConnector().endsWith("Http11AprProtocol")) {
                initHttp11AprProtocol(connector);
            } else {
                initHttp11NioProtocol(connector);
            }
        };
        // 解析含有中文名的文件的url
        if (null != appConfig.getServerProperties().getTomcat().getUriEncoding())
            factory.setUriEncoding(appConfig.getServerProperties().getTomcat().getUriEncoding());
        factory.addConnectorCustomizers(connectorCustomizer);

        return factory;
    }

    private void initHttp11AprProtocol(Connector connector) {
        Http11AprProtocol protocol = (Http11AprProtocol) connector.getProtocolHandler();
        setConfig(protocol);
    }


    private void initHttp11NioProtocol(Connector connector) {
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        setConfig(protocol);
    }

    private void setConfig(AbstractHttp11Protocol protocol) {
        // 设置最大连接数
        if (appConfig.getServerProperties().getTomcat().getMaxConnections() > 0)
            protocol.setMaxConnections(appConfig.getServerProperties().getTomcat().getMaxConnections());
        // 设置最大线程数
        if (appConfig.getServerProperties().getTomcat().getMaxThreads() > 0)
            protocol.setMaxThreads(appConfig.getServerProperties().getTomcat().getMaxThreads());
        // 最小备用线程数
        if (appConfig.getServerProperties().getTomcat().getMinSpareThreads() > 0)
            protocol.setMinSpareThreads(appConfig.getServerProperties().getTomcat().getMinSpareThreads());
        if (appConfig.getServerProperties().getTomcat().getAcceptCount() > 0)
            protocol.setAcceptorThreadCount(appConfig.getServerProperties().getTomcat().getAcceptCount());
        if (null != properties.getConnectionTimeout()) {
            protocol.setMaxKeepAliveRequests(properties.getConnectionTimeout());
        }
        if (null != properties.getKeepAliveTimeout()) {
            protocol.setKeepAliveTimeout(properties.getKeepAliveTimeout());
        }
        if (null != properties.getConnectionTimeout()) {
            protocol.setConnectionTimeout(properties.getConnectionTimeout());
        }
    }
}
