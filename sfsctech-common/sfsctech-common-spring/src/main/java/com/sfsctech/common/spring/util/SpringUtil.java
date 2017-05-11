package com.sfsctech.common.spring.util;

import com.sfsctech.common.spring.properties.Application;
import com.sfsctech.common.util.ByteSizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * Class SpringUtil
 *
 * @author 张麒 2017/5/11.
 * @version Description:
 */
@Component
public class SpringUtil {

    @Autowired
    private Application application;

    public ServletRegistrationBean getServletRegistrationBean(DispatcherServlet servlet){
        // 404请求抛出NoHandlerFoundException
        servlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet);
        // 上传文件配置
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(application.getLocation(), ByteSizeUtil.parseBytesSize(application.getMaxFileSize()), ByteSizeUtil.parseBytesSize(application.getMaxRequestSize()), application.getFileSizeThreshold());
        registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }
}
