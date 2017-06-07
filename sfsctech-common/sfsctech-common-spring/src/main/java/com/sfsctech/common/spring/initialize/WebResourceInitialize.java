package com.sfsctech.common.spring.initialize;

import com.sfsctech.common.constants.CommonConstants;
import com.sfsctech.common.spring.properties.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import javax.servlet.ServletContext;

/**
 * Class ResourceInitialize
 *
 * @author 张麒 2017/2/9.
 * @version Description:
 */
@Component
public class WebResourceInitialize extends WebApplicationObjectSupport implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    /**
     * 设置网页资源地址至ServletContext
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        ServletContext servletContext = super.getWebApplicationContext().getServletContext();
        servletContext.setAttribute(CommonConstants.STATIC_RESOURCE, appConfig.STATIC_RESOURCES);
        servletContext.setAttribute(CommonConstants.CONTEXT_PATH, servletContext.getContextPath());
        servletContext.setAttribute(CommonConstants.LOGBACK_CONFIG_FILE_LOCATION_PARAM, appConfig.LOGBACK_CONFIG_URL);
        servletContext.setAttribute(CommonConstants.CONTEXT_PATH, servletContext.getContextPath());
    }
}
