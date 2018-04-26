package com.sfsctech.spring.initialize;

import com.sfsctech.constants.CommonConstants;
import com.sfsctech.spring.properties.AppConfig;
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
    private AppConfig config;

    /**
     * 设置网页资源地址至ServletContext
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        ServletContext servletContext = super.getWebApplicationContext().getServletContext();
        // 静态资源
        servletContext.setAttribute(CommonConstants.STATIC_RESOURCE_ATTRIBUTE, config.getWebsiteProperties().getSupport().getStaticResources());
        // ContextPath
        servletContext.setAttribute(CommonConstants.CONTEXT_PATH_ATTRIBUTE, config.getServerProperties().getContextPath());
        // 网站域名
        servletContext.setAttribute(CommonConstants.WEBSITE_DOMAIN_ATTRIBUTE, config.getServerProperties().getSession().getCookie().getDomain());
    }
}
