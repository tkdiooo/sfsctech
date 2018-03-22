package com.sfsctech.spring.util;

import com.sfsctech.common.util.ByteSizeUtil;
import com.sfsctech.constants.LabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
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
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件上传临时路径
        factory.setLocation(multipart.getLocation());
        // 单个文件大小
        factory.setMaxFileSize(multipart.getMaxFileSize());
        // 批量文件总大小
        factory.setMaxRequestSize(multipart.getMaxRequestSize());
        // 文件写入磁盘的阈值
        factory.setFileSizeThreshold(multipart.getFileSizeThreshold());
        registration.setMultipartConfig(factory.createMultipartConfig());
        return registration;
    }
}
