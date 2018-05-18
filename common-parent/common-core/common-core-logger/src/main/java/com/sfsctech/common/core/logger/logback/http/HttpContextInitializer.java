package com.sfsctech.common.core.logger.logback.http;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.gaffer.GafferUtil;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.classic.util.EnvUtil;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.StatusManager;

import java.net.URL;

/**
 * Class HttpContextInitializer
 *
 * @author 张麒 2017/6/7.
 * @version Description:
 */
public class HttpContextInitializer extends ContextInitializer {

    private final LoggerContext loggerContext;

    public HttpContextInitializer(LoggerContext loggerContext) {
        super(loggerContext);
        this.loggerContext = loggerContext;
    }

    @Override
    public void configureByResource(URL url) throws JoranException {
        if (url == null) {
            throw new IllegalArgumentException("URL argument cannot be null");
        }
        if (url.toString().endsWith("groovy")) {
            if (EnvUtil.isGroovyAvailable()) {
                // avoid directly referring to GafferConfigurator so as to avoid
                // loading  groovy.lang.GroovyObject . See also http://jira.qos.ch/browse/LBCLASSIC-214
                GafferUtil.runGafferConfiguratorOn(loggerContext, this, url);
            } else {
                StatusManager sm = loggerContext.getStatusManager();
                sm.add(new ErrorStatus("Groovy classes are not available on the class path. ABORTING INITIALIZATION.",
                        loggerContext));
            }
        } else if (url.toString().endsWith("xml")) {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doConfigure(url);
        } else if (url.toString().startsWith("http:")) {
            HttpJoranConfigurator configurator = new HttpJoranConfigurator();
            configurator.setContext(loggerContext);
            configurator.doHttpConfigure(url);
        } else {
            throw new LogbackException("Unexpected filename extension of file [" + url.toString() + "]. Should be either .groovy or .xml");
        }
    }
}
