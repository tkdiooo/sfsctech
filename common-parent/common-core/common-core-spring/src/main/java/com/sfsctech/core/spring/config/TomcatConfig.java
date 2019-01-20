package com.sfsctech.core.spring.config;

import com.sfsctech.core.spring.properties.TomcatProperties;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Class TomcatConfig
 *
 * @author 张麒 2018-5-11.
 * @version Description:
 */
@Configuration
@ConditionalOnProperty(name = "server.tomcat.optimize", havingValue = "true")
@Import({TomcatProperties.class})
public class TomcatConfig {

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public TomcatServletWebServerFactory servletContainer(TomcatProperties properties) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setProtocol(properties.getConnector());
        // 解析含有中文名的文件的url
        if (null != serverProperties.getTomcat().getUriEncoding())
            factory.setUriEncoding(serverProperties.getTomcat().getUriEncoding());
        // 连接池策略
        TomcatConnectorCustomizer connectorCustomizer = connector -> {
            if (properties.getConnector().endsWith("Http11AprProtocol")) {
                setConfig((Http11AprProtocol) connector.getProtocolHandler(), properties);
            } else {
                setConfig((Http11NioProtocol) connector.getProtocolHandler(), properties);
            }
        };
        factory.addConnectorCustomizers(connectorCustomizer);
        // cookie策略
        factory.addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
        // TODO SSL配置
//        factory.addAdditionalTomcatConnectors(initiateHttpConnector());
        return factory;
    }

    private void setConfig(AbstractHttp11Protocol protocol, TomcatProperties properties) {
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

//    private Connector initiateHttpConnector() {
//        Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(port);
//        return connector;
//    }
}