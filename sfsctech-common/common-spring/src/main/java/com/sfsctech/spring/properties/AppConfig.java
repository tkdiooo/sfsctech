package com.sfsctech.spring.properties;

import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import com.sfsctech.constants.ExcludesConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Class Application
 *
 * @author 张麒 2017/5/9.
 * @version Description:
 */
@Component
public class AppConfig {

    @Autowired
    private WebsiteProperties websiteProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private WebMvcProperties webMvcProperties;

    private String viewTemplate;

    @Bean()
    @ConfigurationProperties(prefix = "server.session.cookie")
    public Config cookieConfig() {
        return new Config();
    }

    public WebsiteProperties getWebsiteProperties() {
        return websiteProperties;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public String getViewTemplate() {
        if (StringUtil.isNotBlank(viewTemplate) && null != webMvcProperties && StringUtils.isNotBlank(webMvcProperties.getView().getSuffix())) {
            this.viewTemplate = LabelConstants.STAR + webMvcProperties.getView().getSuffix() + LabelConstants.COMMA;
        }
        return viewTemplate;
    }

    public Set<String> getFilterExcludes() {
        if (null != websiteProperties.getRequest().getFilterExclude()) {
            Set<String> excludes = new HashSet<>(websiteProperties.getRequest().getFilterExclude());
            excludes.addAll(ExcludesConstants.FILTER_EXCLUDES_VALUE);
            return excludes;
        } else {
            return null;
        }
    }

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_STATIC_PATH_PATTERN + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String staticPath) {
        // 项目ContextPath
        ExcludesConstants.CONTEXT_PATH = serverProperties.getContextPath();
        // 项目Session认证方式
        ExcludesConstants.SESSION_AUTHENTICATION = websiteProperties.getSession().getAuthentication();
        // 过滤器排除 - 项目视图模板、项目ContextPath
        ExcludesConstants.addFilterExcludes(getViewTemplate(), ExcludesConstants.CONTEXT_PATH);
        // CSRF防御拦截排除 - 路径
        if (null != websiteProperties.getRequest().getCsrfExclude())
            ExcludesConstants.CSRF_EXCLUDES_VALUE.addAll(websiteProperties.getRequest().getCsrfExclude());
        // 项目静态资源路径
        if (StringUtil.isNotBlank(staticPath)) {
            // CSRF防御拦截排除 - 项目静态资源
            ExcludesConstants.addCSRFExcludes(staticPath);
            // 过滤器排除 - 项目静态资源
            ExcludesConstants.addFilterExcludes(staticPath);
        }
    }
}
