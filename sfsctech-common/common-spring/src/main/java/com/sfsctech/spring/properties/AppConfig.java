package com.sfsctech.spring.properties;

import com.sfsctech.common.cookie.Config;
import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import com.sfsctech.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Application
 *
 * @author 张麒 2017/5/9.
 * @version Description:
 */
@Component
public class AppConfig {

    @Autowired
    private WebsiteProperties properties;

    // 自定义属性
    //-------------------------------------------------------------------------------------------
//    /**
//     * 静态资源地址
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SUPPORT_STATIC_RESOURCE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String STATIC_RESOURCES;
//    /**
//     * 单点登录 - 登录服务
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SSO_LOGIN_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String SSO_LOGIN_URL;
//    /**
//     * 单点登录 - 验证服务
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SSO_CHECK_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String SSO_CHECK_URL;
//    /**
//     * 单点登录 - 登出服务
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SSO_LOGOUT_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String SSO_LOGOUT_URL;
//    /**
//     * 单点登录 - 找回密码
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SSO_FORGET_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String SSO_FORGET_URL;
//    /**
//     * 单点登录 - 注册服务
//     */
//    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SSO_REGISTER_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
//    public String SSO_REGISTER_URL;

    @Bean()
    @ConfigurationProperties(prefix = "server.session.cookie")
    public Config cookieConfig() {
        return new Config();
    }

    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_SUFFIX + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String suffix,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_CONTEXTPATH + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String contextPath,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_STATIC_PATH_PATTERN + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String staticPath) {
        SecurityConstants.CONTEXT_PATH = contextPath;
        SecurityConstants.SERVER_SUFFIX = StringUtil.isNotBlank(suffix) ? LabelConstants.STAR + suffix + LabelConstants.COMMA : suffix;
        SecurityConstants.SERVICE_AUTHENTICATION = properties.getSession().getAuthentication();
        SecurityConstants.FILTER_EXCLUDES_VALUE.addAll(properties.getRequest().getFilterExclude());
        SecurityConstants.CSRF_EXCLUDES_VALUE.addAll(properties.getRequest().getCsrfExclude());
        if (StringUtil.isNotBlank(staticPath)) {
            SecurityConstants.SERVER_STATIC_PATH = staticPath;
            SecurityConstants.addCSRFExcludes(staticPath);
        }
    }
}
