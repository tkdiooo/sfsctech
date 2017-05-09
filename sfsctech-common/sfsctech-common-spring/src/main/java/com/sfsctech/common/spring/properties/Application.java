package com.sfsctech.common.spring.properties;

import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class Application
 *
 * @author 张麒 2017/5/9.
 * @version Description:
 */
@Component
public class Application {

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_LOCATION + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    private String location;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_MAX_FILE_SIZE + LabelConstants.COLON + "-1" + LabelConstants.CLOSE_CURLY_BRACE)
    private String maxFileSize;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_MAX_REQUEST_SIZE + LabelConstants.COLON + "-1" + LabelConstants.CLOSE_CURLY_BRACE)
    private String maxRequestSize;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.MULTIPART_FILE_SIZE_THRESHOLD + LabelConstants.COLON + "0" + LabelConstants.CLOSE_CURLY_BRACE)
    private int fileSizeThreshold;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.PROPERTY_STATIC_RESOURCE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    private String staticResources;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.PROPERTY_SSO_LOGIN_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    private String ssoLoginUrl;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.PROPERTY_SSO_CHECK_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    private String ssoCheckUrl;

    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.PROPERTY_SSO_LOGOUT_URL + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    private String ssoLogoutUrl;

    /**
     * 上传文件-存放生成的文件地址
     */
    public String getLocation() {
        return location;
    }

    /**
     * 上传文件-允许上传的单个文件最大值。默认值为 -1，表示没有限制
     *
     * @return
     */
    public String getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * 上传文件-针对该 multipart/form-data 上传文件的最大值，默认值为 -1，表示没有限制
     *
     * @return
     */
    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    /**
     * 上传文件-当数据量大于该值时，内容将被写入文件
     *
     * @return
     */
    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    /**
     * 静态资源地址
     *
     * @return
     */
    public String getStaticResources() {
        return staticResources;
    }

    /**
     * 单点登录 - 登录服务
     *
     * @return
     */
    public String getSsoLoginUrl() {
        return ssoLoginUrl;
    }

    /**
     * 单点登录 - 验证服务
     *
     * @return
     */
    public String getSsoCheckUrl() {
        return ssoCheckUrl;
    }

    /**
     * 单点登录 - 登出服务
     *
     * @return
     */
    public String getSsoLogoutUrl() {
        return ssoLogoutUrl;
    }
}
