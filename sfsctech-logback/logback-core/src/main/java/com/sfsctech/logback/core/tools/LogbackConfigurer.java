package com.sfsctech.logback.core.tools;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.ContextSelector;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.joran.spi.JoranException;
import com.sfsctech.logback.core.http.HttpContextInitializer;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.util.SystemPropertyUtils;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class LogbackConfigurer
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
public class LogbackConfigurer {


    private LogbackConfigurer() {
    }

    public static void initLogging(String location) throws FileNotFoundException, JoranException, MalformedURLException {
        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
        URL url = null;

        if (resolvedLocation.startsWith("classpath:")) {
            String path = resolvedLocation.substring("classpath:".length());
            url = LogbackConfigurer.class.getResource(path);
            if (url == null) {
                String description = "class path resource [" + path + "]";
                throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");
            }
        } else if (resolvedLocation.startsWith("file:") || resolvedLocation.startsWith("http:")) {
            url = new URL(resolvedLocation);

        }
        LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        loggerContext.reset();
        new HttpContextInitializer(loggerContext).configureByResource(url);
    }

    public static void shutdownLogging() {
        ContextSelector selector = ContextSelectorStaticBinder.getSingleton().getContextSelector();
        LoggerContext loggerContext = selector.getLoggerContext();
        String loggerContextName = loggerContext.getName();
        LoggerContext context = selector.detachLoggerContext(loggerContextName);
        context.reset();
    }
}
