package com.sfsctech.core.logger.logback.listener;



import com.sfsctech.core.logger.logback.tools.WebLogbackConfigurer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Class LogbackConfigListener
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
public class LogbackConfigListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        WebLogbackConfigurer.shutdownLogging(event.getServletContext());
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebLogbackConfigurer.initLogging(event.getServletContext());
    }
}
