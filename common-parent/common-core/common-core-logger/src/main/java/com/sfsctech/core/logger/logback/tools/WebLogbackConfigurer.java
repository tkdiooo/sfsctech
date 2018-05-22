package com.sfsctech.core.logger.logback.tools;

import ch.qos.logback.core.joran.spi.JoranException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

/**
 * Class LogbackUtil
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
public class WebLogbackConfigurer {

    private static final String LOGBACK_EXPOSE_WEB_APP_ROOT_PARAM = "logback-expose-webapp-root";

    public static void initLogging(ServletContext servletContext) {
        if (exposeWebAppRoot(servletContext)) {
            WebUtils.setWebAppRootSystemProperty(servletContext);
        }

        String location = servletContext.getInitParameter(LOGBACK_EXPOSE_WEB_APP_ROOT_PARAM);

        try {
            Class<?> julBridge = ClassUtils.forName("org.slf4j.bridge.SLF4JBridgeHandler", ClassUtils.getDefaultClassLoader());

            Method removeHandlers = ReflectionUtils.findMethod(julBridge, "removeHandlersForRootLogger");
            if (removeHandlers != null) {
                servletContext.log("Removing all previous handlers for JUL to SLF4J bridge");
                ReflectionUtils.invokeMethod(removeHandlers, null);
            }

            Method install = ReflectionUtils.findMethod(julBridge, "install");
            if (install != null) {
                servletContext.log("Installing JUL to SLF4J bridge");
                ReflectionUtils.invokeMethod(install, null);
            }
        } catch (ClassNotFoundException ignored) {
            servletContext.log("JUL to SLF4J bridge is not available on the classpath");
        }

        if (location != null) {
            try {
                location = SystemPropertyUtils.resolvePlaceholders(location);
                if (!ResourceUtils.isUrl(location)) {
                    location = WebUtils.getRealPath(servletContext, location);
                }

                servletContext.log("Initializing Logback from [" + location + "]");

                LogbackConfigurer.initLogging(location);
            } catch (FileNotFoundException ex) {
                throw new IllegalArgumentException("Invalid 'logbackConfigLocation' parameter: " + ex.getMessage());
            } catch (JoranException e) {
                throw new RuntimeException("Unexpected error while configuring logback", e);
            } catch (MalformedURLException me) {
                throw new RuntimeException("Invalid 'logbackConfigLocation' parameter: " + me.getMessage());
            }
        }
    }

    public static void shutdownLogging(ServletContext servletContext) {
        try {
            Class<?> julBridge = ClassUtils.forName("org.slf4j.bridge.SLF4JBridgeHandler", ClassUtils.getDefaultClassLoader());
            Method uninstall = ReflectionUtils.findMethod(julBridge, "uninstall");
            if (uninstall != null) {
                servletContext.log("Uninstalling JUL to SLF4J bridge");
                ReflectionUtils.invokeMethod(uninstall, null);
            }
        } catch (ClassNotFoundException ignored) {
        }

        try {
            servletContext.log("Shutting down Logback");
            LogbackConfigurer.shutdownLogging();
        } finally {
            if (exposeWebAppRoot(servletContext)) {
                WebUtils.removeWebAppRootSystemProperty(servletContext);
            }
        }
    }

    private static boolean exposeWebAppRoot(ServletContext servletContext) {
        String exposeWebAppRootParam = servletContext.getInitParameter(LOGBACK_EXPOSE_WEB_APP_ROOT_PARAM);
        return (exposeWebAppRootParam == null || Boolean.valueOf(exposeWebAppRootParam));
    }

}
