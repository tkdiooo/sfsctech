package com.sfsctech.core.web.initialize;

import com.sfsctech.core.base.constants.CommonConstants;
import com.sfsctech.core.web.properties.WebsiteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
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
    private WebsiteProperties websiteProperties;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * 设置网页资源地址至ServletContext
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        ServletContext servletContext = super.getWebApplicationContext().getServletContext();
        // 静态资源
        servletContext.setAttribute(CommonConstants.STATIC_RESOURCE_ATTRIBUTE, websiteProperties.getSupport().getStaticResources());
        // ContextPath
        servletContext.setAttribute(CommonConstants.CONTEXT_PATH_ATTRIBUTE, serverProperties.getServlet().getContextPath());
        // 网站域名
        servletContext.setAttribute(CommonConstants.WEBSITE_DOMAIN_ATTRIBUTE, serverProperties.getServlet().getSession().getCookie().getDomain());
    }
}
