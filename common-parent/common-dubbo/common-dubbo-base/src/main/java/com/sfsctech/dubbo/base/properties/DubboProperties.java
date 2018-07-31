package com.sfsctech.dubbo.base.properties;

import com.alibaba.dubbo.config.ProtocolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 协议配置
    public enum Config {
        Multiple, Single
    }

    // 序列化
    public enum SerializeOptimizer {
        Kryo
    }

    private final DubboProperties.Application application;
    private final DubboProperties.Registry registry;
    private final DubboProperties.Protocol protocol;
    private final DubboProperties.Rpc rpc;
    private final DubboProperties.DevSetting devSetting;

    public DubboProperties() {
        this.application = new DubboProperties.Application();
        this.registry = new DubboProperties.Registry();
        this.protocol = new DubboProperties.Protocol();
        this.rpc = new DubboProperties.Rpc();
        this.devSetting = new DubboProperties.DevSetting();
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

    public DevSetting getDevSetting() {
        return devSetting;
    }


    public static class Application {

        private String name;
        private String logger;

        Application() {
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

        private String protocol;
        private String address;
        private boolean check;
        private boolean register;
        private boolean subscribe;
        private int timeout;

        Registry() {
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
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
        private SerializeOptimizer optimizer;
        private Config config;

        private final Protocol.Single single;
        private final Map<String, ProtocolConfig> multiple;

        Protocol() {
            this.single = new Single();
            this.multiple = new HashMap<>();
        }

        public SerializeOptimizer getOptimizer() {
            return optimizer;
        }

        public void setOptimizer(SerializeOptimizer optimizer) {
            this.optimizer = optimizer;
        }

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        public Single getSingle() {
            return single;
        }

        public Map<String, ProtocolConfig> getMultiple() {
            return multiple;
        }

        public static class Single {
            private String name;
            private int port;
            private String server;

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

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }
        }
    }

    public static class Rpc {
        private String servicePackage;
        private Integer concurrency = 40;

        Rpc() {

        }

        public String getServicePackage() {
            return servicePackage;
        }

        public void setServicePackage(String servicePackage) {
            this.servicePackage = servicePackage;
        }

        public Integer getConcurrency() {
            return concurrency;
        }

        public void setConcurrency(Integer concurrency) {
            this.concurrency = concurrency;
        }
    }


    public static class DevSetting {

        private String systemPath;
        private List<Develop> develop;

        DevSetting() {
        }

        public String getSystemPath() {
            return systemPath;
        }

        public void setSystemPath(String systemPath) {
            this.systemPath = systemPath;
        }

        public List<Develop> getDevelop() {
            return develop;
        }

        public void setDevelop(List<Develop> develop) {
            this.develop = develop;
        }


        public static class Develop {
            private String name;
            private int port;
            private String infPackage;

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

            public String getInfPackage() {
                return infPackage;
            }

            public void setInfPackage(String infPackage) {
                this.infPackage = infPackage;
            }
        }
    }

}
