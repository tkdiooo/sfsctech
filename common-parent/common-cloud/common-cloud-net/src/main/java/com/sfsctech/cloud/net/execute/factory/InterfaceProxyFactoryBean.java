package com.sfsctech.cloud.net.execute.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class InterfaceProxyFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {
    private Object interfaceProxy;
    private Class interfaceType;

    @Autowired
    private InterfaceProxyFactory interfaceProxyFactory;

    public InterfaceProxyFactoryBean() {
    }

    public InterfaceProxyFactoryBean(Class interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Object getObject() {
        return this.interfaceProxy;
    }

    public Class<?> getObjectType() {
        return this.interfaceType;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() {
        this.interfaceProxy = this.interfaceProxyFactory.createProxy(this.interfaceType);
    }

    public Object getInterfaceProxy() {
        return this.interfaceProxy;
    }

    public void setInterfaceProxy(Object interfaceProxy) {
        this.interfaceProxy = interfaceProxy;
    }

    public Class getInterfaceType() {
        return this.interfaceType;
    }

    public void setInterfaceType(Class interfaceType) {
        this.interfaceType = interfaceType;
    }

    public InterfaceProxyFactory getInterfaceProxyFactory() {
        return this.interfaceProxyFactory;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
}
