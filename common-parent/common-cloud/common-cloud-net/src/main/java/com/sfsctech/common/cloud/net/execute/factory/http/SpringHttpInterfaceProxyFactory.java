package com.sfsctech.common.cloud.net.execute.factory.http;

import com.sfsctech.common.cloud.net.domain.ServiceInterface;
import com.sfsctech.common.cloud.net.execute.factory.InterfaceProxyFactory;
import com.sfsctech.common.cloud.net.execute.handler.ExecuteHandler;
import com.sfsctech.common.cloud.net.execute.handler.SpringHttpInterfaceExecuteHandler;
import com.sfsctech.common.cloud.net.resolver.InterfaceResolver;
import com.sfsctech.common.cloud.net.resolver.SpringHttpInterfaceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Proxy;

/**
 * Class ServiceInterface
 * 接口代理工厂
 *
 * @author 张麒 2018-5-9.
 * @version Description:
 */
public class SpringHttpInterfaceProxyFactory implements InterfaceProxyFactory {

    private RestTemplate httpClient;
    // 接口解析器
    private InterfaceResolver<Class> interfaceResolver = new SpringHttpInterfaceResolver();
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceProxyFactory.class);

    public SpringHttpInterfaceProxyFactory(RestTemplate httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 创建代理
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxy(Class<T> cls) {
        ServiceInterface interfaceInfo = this.interfaceResolver.parse(cls);
        LOGGER.info("{} parsing the success.", cls.getName());
        ExecuteHandler executeHandler = new SpringHttpInterfaceExecuteHandler(interfaceInfo, this.httpClient);
        LOGGER.info("{} execute handler initialize success.", interfaceInfo.getInterfaceClass().getName());
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, executeHandler);
    }
}
