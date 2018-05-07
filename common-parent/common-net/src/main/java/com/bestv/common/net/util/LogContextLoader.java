//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.util;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.impl.StaticLoggerBinder;

public class LogContextLoader {
    public LogContextLoader() {
    }

    public static LoggerContext getLoggerContext() {
        return (LoggerContext)StaticLoggerBinder.getSingleton().getLoggerFactory();
    }
}
