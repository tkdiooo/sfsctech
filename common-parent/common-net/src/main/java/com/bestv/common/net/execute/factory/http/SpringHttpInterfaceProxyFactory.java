//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.execute.factory.http;

import com.bestv.common.net.domain.InterfaceInfo;
import com.bestv.common.net.execute.factory.InterfaceProxyFactory;
import com.bestv.common.net.execute.handler.ExecuteHandler;
import com.bestv.common.net.execute.handler.SpringHttpInterfaceExecuteHandler;
import com.bestv.common.net.resolver.InterfaceResolver;
import com.bestv.common.net.resolver.SpringHttpInterfaceResolver;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 接口代理工厂
 */
public class SpringHttpInterfaceProxyFactory implements InterfaceProxyFactory {
    private RestTemplate httpClient;
    private InterfaceResolver<Class> interfaceResolver = new SpringHttpInterfaceResolver();
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceProxyFactory.class);

    public SpringHttpInterfaceProxyFactory(RestTemplate httpClient) {
        this.httpClient = httpClient;
    }

    public <T> T createProxy(Class<T> interfaceType) {
        InterfaceInfo interfaceInfo = this.interfaceResolver.parse(interfaceType);
        LOGGER.info("{} 接口信息解析成功.", interfaceType.getName());
        ExecuteHandler executeHandler = new SpringHttpInterfaceExecuteHandler(interfaceInfo, this.httpClient);
        LOGGER.info("{} 远程调用执行器初始化成功.", interfaceInfo.getInterfaceClass().getName());
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, executeHandler);
    }
}
