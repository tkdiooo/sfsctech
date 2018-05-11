package com.sfsctech.common.core.spring.config;

import com.sfsctech.common.core.spring.condition.TomcatCondition;
import com.sfsctech.common.core.spring.properties.TomcatProperties;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class TomcatConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@Conditional(TomcatCondition.class)
@Import({ServerProperties.class, TomcatProperties.class})
public class TomcatConfig {

    @Autowired
    private ServerProperties serverProperties;

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
        if (null != serverProperties.getTomcat().getUriEncoding())
            factory.setUriEncoding(serverProperties.getTomcat().getUriEncoding());
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
        if (serverProperties.getTomcat().getMaxConnections() > 0)
            protocol.setMaxConnections(serverProperties.getTomcat().getMaxConnections());
        // 设置最大线程数
        if (serverProperties.getTomcat().getMaxThreads() > 0)
            protocol.setMaxThreads(serverProperties.getTomcat().getMaxThreads());
        // 最小备用线程数
        if (serverProperties.getTomcat().getMinSpareThreads() > 0)
            protocol.setMinSpareThreads(serverProperties.getTomcat().getMinSpareThreads());
        if (serverProperties.getTomcat().getAcceptCount() > 0)
            protocol.setAcceptorThreadCount(serverProperties.getTomcat().getAcceptCount());
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