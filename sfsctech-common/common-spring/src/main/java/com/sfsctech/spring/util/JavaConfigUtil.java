package com.sfsctech.spring.util;

import com.sfsctech.common.util.ByteSizeUtil;
import com.sfsctech.constants.LabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
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
public class JavaConfigUtil {

    @Autowired
    private MultipartProperties multipart;

    public ServletRegistrationBean getServletRegistrationBean(DispatcherServlet servlet) {
        // 404请求抛出NoHandlerFoundException
        servlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistrationBean registration = new ServletRegistrationBean(servlet);
        registration.addInitParameter("defaultHtmlEscape", LabelConstants.TRUE);
        // 上传文件配置
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(multipart.getLocation(), ByteSizeUtil.parseBytesSize(multipart.getMaxFileSize()), ByteSizeUtil.parseBytesSize(multipart.getMaxRequestSize()), Integer.valueOf(multipart.getFileSizeThreshold()));
        registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }
}
