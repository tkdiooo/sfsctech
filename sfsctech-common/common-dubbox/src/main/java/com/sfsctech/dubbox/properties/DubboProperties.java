package com.sfsctech.dubbox.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class DubboProperties
 *
 * @author 张麒 2017/9/4.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "dubbo"
)
public class DubboProperties {

    private final DubboProperties.Application application;
    private final DubboProperties.Registry registry;
    private final DubboProperties.Protocol protocol;
    private final DubboProperties.Rpc rpc;

    public DubboProperties() {
        this.application = new DubboProperties.Application();
        this.registry = new DubboProperties.Registry();
        this.protocol = new DubboProperties.Protocol();
        this.rpc = new DubboProperties.Rpc();
    }

    public Application getApplication() {
        return application;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Rpc getRpc() {
        return rpc;
    }

    public static class Application {

        private String name;
        private String logger;

        public Application() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
        }
    }

    public static class Registry {

        private String address;
        private boolean check;
        private boolean register;
        private boolean subscribe;
        private int timeout;

        public Registry() {
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public boolean isRegister() {
            return register;
        }

        public void setRegister(boolean register) {
            this.register = register;
        }

        public boolean isSubscribe() {
            return subscribe;
        }

        public void setSubscribe(boolean subscribe) {
            this.subscribe = subscribe;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public static class Protocol {
        private String name = "dubbo";
        private int port = 20880;
        private String server;
        private boolean kryo;
        private Map<String, String> map = new HashMap<>();

        public Protocol() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isKryo() {
            return kryo;
        }

        public void setKryo(boolean kryo) {
            this.kryo = kryo;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public Map<String, String> getMap() {
            map.put("pro1", "1111");
            map.put("pro2", "2222");
            map.put("pro3", "3333");
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }
    }

    public static class Rpc {
        private String servicePackage;

        public Rpc() {

        }

        public String getServicePackage() {
            return servicePackage;
        }

        public void setServicePackage(String servicePackage) {
            this.servicePackage = servicePackage;
        }
    }
}
