//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.log.factory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.util.FileSize;
import com.bestv.common.net.log.CommonNetLogger;
import com.bestv.common.net.log.GenericCommonNetLogger;
import com.bestv.common.net.log.LoggerTypeEnum;
import com.bestv.common.net.log.appender.CommonNetAppender;
import com.bestv.common.net.util.LogContextLoader;
import com.bestv.common.util.log.PatchedSizeAndTimeBasedRollingPolicy;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class GenericCommonNetLoggerFactory implements CommonNetLoggerFactory<Class> {
    private static final String LOG_OUTPUT_PATTERN = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
    private static final String CLIENT_LOG_FILE_NAME_PATTERN = "logs/rpc/client/%d{yyyy-MM-dd}.%i.log";
    private static final String SERVER_LOG_FILE_NAME_PATTERN = "logs/rpc/server/%d{yyyy-MM-dd}.%i.log";
    private static final Integer MAX_SAVE_DAYS = 30;
    private static final Long MAX_PER_FILE_SIZE = 10485760L;
    private static final Long MAX_LOG_FILES_SIZE = 3221225472L;
    private static final Map<LoggerTypeEnum, String> LOGGER_TYPE_ENUM_FILE_PATTERN_MAP = new HashMap();
    private static final Map<LoggerTypeEnum, CommonNetAppender> LOGGER_TYPE_ENUM_APPENDER_MAP = new HashMap();

    public GenericCommonNetLoggerFactory() {
    }

    public CommonNetLogger getLogger(Class loggerTarget, LoggerTypeEnum loggerTypeEnum) {
        Logger logger = (Logger)LoggerFactory.getLogger(loggerTarget);
        CommonNetAppender appender = (CommonNetAppender)LOGGER_TYPE_ENUM_APPENDER_MAP.get(loggerTypeEnum);
        if (appender == null) {
            Map var5 = LOGGER_TYPE_ENUM_APPENDER_MAP;
            synchronized(LOGGER_TYPE_ENUM_APPENDER_MAP) {
                if (LOGGER_TYPE_ENUM_APPENDER_MAP.get(loggerTypeEnum) == null) {
                    appender = buildAppender(loggerTypeEnum);
                    LOGGER_TYPE_ENUM_APPENDER_MAP.put(loggerTypeEnum, appender);
                }
            }
        }

        logger.addAppender(appender);
        logger.setAdditive(false);
        logger.setLevel(Level.INFO);
        return new GenericCommonNetLogger(logger);
    }

    private static CommonNetAppender buildAppender(LoggerTypeEnum loggerTypeEnum) {
        final CommonNetAppender appender = new CommonNetAppender();
        final PatchedSizeAndTimeBasedRollingPolicy rollingPolicy = new PatchedSizeAndTimeBasedRollingPolicy();
        rollingPolicy.setContext(LogContextLoader.getLoggerContext());
        rollingPolicy.setMaxFileSize(new FileSize(MAX_PER_FILE_SIZE));
        rollingPolicy.setFileNamePattern((String)LOGGER_TYPE_ENUM_FILE_PATTERN_MAP.get(loggerTypeEnum));
        rollingPolicy.setMaxHistory(MAX_SAVE_DAYS);
        rollingPolicy.setTotalSizeCap(new FileSize(MAX_LOG_FILES_SIZE));
        rollingPolicy.setParent(appender);
        rollingPolicy.start();
        final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(LogContextLoader.getLoggerContext());
        encoder.setCharset(Charset.forName("utf-8"));
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();
        appender.setRollingPolicy(rollingPolicy);
        appender.setContext(LogContextLoader.getLoggerContext());
        appender.setEncoder(encoder);
        appender.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                appender.stop();
                encoder.stop();
                rollingPolicy.stop();
            }
        }));
        return appender;
    }

    static {
        LOGGER_TYPE_ENUM_FILE_PATTERN_MAP.put(LoggerTypeEnum.CLIENT, "logs/rpc/client/%d{yyyy-MM-dd}.%i.log");
        LOGGER_TYPE_ENUM_FILE_PATTERN_MAP.put(LoggerTypeEnum.SERVER, "logs/rpc/server/%d{yyyy-MM-dd}.%i.log");
    }
}
