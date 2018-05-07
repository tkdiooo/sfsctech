//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadFactoryBuilder {
    public ThreadFactoryBuilder() {
    }

    public static ThreadFactoryBuilder.ThreadFactoryGen build() {
        return new ThreadFactoryBuilder.ThreadFactoryGen();
    }

    public static class ThreadFactoryGen implements ThreadFactory {
        private String threadNamePrefix;
        private int priority = 5;
        private boolean daemon = false;
        private AtomicInteger threadNum = new AtomicInteger(0);
        private Map<String, AtomicBoolean> propertiesSetStatus = new ConcurrentHashMap();
        private static final String NAME_PREFIX_KEY = "namePrefix";
        private static final String PRIORITY_KEY = "priority";
        private static final String DAEMON_KEY = "daemon";
        private static final String RUN_KEY = "run";

        public ThreadFactoryGen() {
        }

        public Thread newThread(Runnable r) {
            if (r == null) {
                throw new RuntimeException("runnable 对象为空");
            } else {
                this.activeEnvSetProtect("run");
                Thread thread = new Thread(r);
                thread.setName(this.buildNewThreadName());
                thread.setPriority(this.priority);
                thread.setDaemon(this.daemon);
                return thread;
            }
        }

        public ThreadFactoryBuilder.ThreadFactoryGen namePrefix(String prefix) {
            this.checkEnv("namePrefix", "已经设置过线程名前缀, 无法重复设置");
            this.threadNamePrefix = prefix;
            this.activeEnvSetProtect("namePrefix");
            return this;
        }

        public ThreadFactoryBuilder.ThreadFactoryGen daemon(boolean daemon) {
            this.checkEnv("daemon", "已经设置过线程是否为守护线程, 无法重复设置");
            this.daemon = daemon;
            this.activeEnvSetProtect("daemon");
            return this;
        }

        public ThreadFactoryBuilder.ThreadFactoryGen priority(int priority) {
            this.checkEnv("priority", "已经设置过线程优先级, 无法重复设置");
            this.priority = priority;
            this.activeEnvSetProtect("priority");
            return this;
        }

        private String buildNewThreadName() {
            return this.threadNamePrefix + this.threadNum.getAndIncrement();
        }

        private void checkEnv(String envKey, String errorMessage) {
            AtomicBoolean runStatus = (AtomicBoolean)this.propertiesSetStatus.get("run");
            if (runStatus != null && runStatus.get()) {
                throw new IllegalStateException("线程工厂已开始工作, 无法进行设置");
            } else {
                AtomicBoolean envStatus = (AtomicBoolean)this.propertiesSetStatus.get(envKey);
                if (envStatus != null && envStatus.get()) {
                    throw new IllegalStateException(errorMessage);
                }
            }
        }

        private void activeEnvSetProtect(String envKey) {
            this.propertiesSetStatus.put(envKey, new AtomicBoolean(true));
        }
    }
}
