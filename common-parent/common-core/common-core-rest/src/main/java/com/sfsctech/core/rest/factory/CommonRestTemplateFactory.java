package com.sfsctech.core.rest.factory;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sfsctech.core.rest.properties.RestProperties;
import com.sfsctech.core.rest.util.UTF8PatcherForHttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

/**
 * Class CommonRestTemplateFactory
 *
 * @author 张麒 2018-5-15.
 * @version Description:
 */
public class CommonRestTemplateFactory implements RestTemplateFactory {

    private RestProperties properties;
    private FastJsonHttpMessageConverter converter;

    public CommonRestTemplateFactory(RestProperties properties, FastJsonHttpMessageConverter converter) {
        this.properties = properties;
        this.converter = converter;
    }

    public RestTemplate buildSimpleRest() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
        factory.setConnectTimeout(properties.getConnectionTimeout());
        factory.setReadTimeout(properties.getReadTimeout());
        return UTF8PatcherForHttpClient.patch(new RestTemplate(factory), converter);
    }

    public RestTemplate buildPoolRest() {
        try {
            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial(null, (arg0, arg1) -> true).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();

            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(properties.getMaxTotal());
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(properties.getMaxPerRoute());

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                    .setSSLContext(sslContext)
                    .setConnectionManager(poolingHttpClientConnectionManager)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
            factory.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
            factory.setConnectTimeout(properties.getConnectionTimeout());
            factory.setReadTimeout(properties.getReadTimeout());
            return UTF8PatcherForHttpClient.patch(new RestTemplate(factory), converter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AsyncRestTemplate buildAsyncRest() {
        try {
            SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial(null, (arg0, arg1) -> true).build();
            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                    .<SchemeIOSessionStrategy>create()
                    .register("http", NoopIOSessionStrategy.INSTANCE)
                    .register("https", new SSLIOSessionStrategy(sslContext)).build();
            // 配置io线程
            IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();
            // 设置连接池大小
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
            PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(ioReactor, sessionStrategyRegistry);
            HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClients.custom()
                    .setSSLContext(sslContext)
                    .setConnectionManager(conMgr)
                    .setDefaultCookieStore(new BasicCookieStore());
            HttpComponentsAsyncClientHttpRequestFactory factory = new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClientBuilder.build());
            factory.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
            factory.setConnectTimeout(properties.getConnectionTimeout());
            factory.setReadTimeout(properties.getReadTimeout());
            return new AsyncRestTemplate(factory, UTF8PatcherForHttpClient.patch(new RestTemplate(factory), converter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
