package com.sfsctech.spring.properties;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.SecurityConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class Application
 *
 * @author 张麒 2017/5/9.
 * @version Description:
 */
@Component
public class AppConfig {

    // 文件上传属性
    //-------------------------------------------------------------------------------------------
    /**
     * 上传文件-存放生成的文件地址
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_LOCATION + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String MULTIPART_LOCATION;
    /**
     * 上传文件-允许上传的单个文件最大值。默认值为 -1，表示没有限制
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_MAX_FILE_SIZE + LabelConstants.COLON + "-1" + LabelConstants.CLOSE_CURLY_BRACE)
    public String MULTIPART_MAX_FILE_SIZE;
    /**
     * 上传文件-针对该 multipart/form-data 上传文件的最大值，默认值为 -1，表示没有限制
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_MAX_REQUEST_SIZE + LabelConstants.COLON + "-1" + LabelConstants.CLOSE_CURLY_BRACE)
    public String MULTIPART_MAX_REQUEST_SIZE;
    /**
     * 上传文件-当数据量大于该值时，内容将被写入文件
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_FILE_SIZE_THRESHOLD + LabelConstants.COLON + "0" + LabelConstants.CLOSE_CURLY_BRACE)
    public int MULTIPART_FILE_SIZE_THRESHOLD;

    // 自定义属性
    //-------------------------------------------------------------------------------------------
    /**
     * 静态资源地址
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_SUPPORT_STATIC_RESOURCE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String STATIC_RESOURCES;
    /**
     * 单点登录 - 登录服务
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SSO_LOGIN_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String SSO_LOGIN_URL;
    /**
     * 单点登录 - 验证服务
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SSO_CHECK_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String SSO_CHECK_URL;
    /**
     * 单点登录 - 登出服务
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SSO_LOGOUT_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String SSO_LOGOUT_URL;

    @Autowired
    public void staticAttribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_SUFFIX + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String suffix,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_CONTEXTPATH + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String contextPath,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_STATIC_PATH_PATTERN + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String staticPath,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.SERVER_SOA + LabelConstants.COLON + LabelConstants.FALSE + LabelConstants.CLOSE_CURLY_BRACE) boolean soa,
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.WEBSITE_REQUEST_FILTER_EXCLUDE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String[] exclude) {
        SecurityConstants.CONTEXT_PATH = contextPath;
        SecurityConstants.SERVER_SOA = soa;
        SecurityConstants.SERVER_SUFFIX = StringUtil.isNotBlank(suffix) ? LabelConstants.STAR + suffix + LabelConstants.COMMA : suffix;
        SecurityConstants.SERVER_STATIC_PATH = staticPath;
        SecurityConstants.addFilterExcludes(exclude);
    }
}
