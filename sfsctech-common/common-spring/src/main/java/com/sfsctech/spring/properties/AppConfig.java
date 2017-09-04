package com.sfsctech.spring.properties;

import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import com.sfsctech.constants.ExcludesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_STATIC_PATH_PATTERN + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String staticPath) {
        ExcludesConstants.CONTEXT_PATH = serverProperties.getContextPath();
        ExcludesConstants.SESSION_AUTHENTICATION = websiteProperties.getSession().getAuthentication();
        if (null != websiteProperties.getRequest().getFilterExclude())
            ExcludesConstants.FILTER_EXCLUDES_VALUE.addAll(websiteProperties.getRequest().getFilterExclude());
        if (null != websiteProperties.getRequest().getCsrfExclude())
            ExcludesConstants.CSRF_EXCLUDES_VALUE.addAll(websiteProperties.getRequest().getCsrfExclude());
        if (StringUtil.isNotBlank(staticPath)) {
            ExcludesConstants.addCSRFExcludes(staticPath);
        }
    }
}
