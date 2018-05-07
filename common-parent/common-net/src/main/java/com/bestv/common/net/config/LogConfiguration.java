//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import ch.qos.logback.classic.LoggerContext;
import com.bestv.common.net.log.factory.CommonNetLoggerFactory;
import com.bestv.common.net.log.factory.GenericCommonNetLoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({LoggerContext.class})
public class LogConfiguration {
    public LogConfiguration() {
    }

    @Bean
    public CommonNetLoggerFactory getCommonNetLoggerFactory() {
        return new GenericCommonNetLoggerFactory();
    }
}
