package com.sfsctech.common.constants;

/**
 * Class PropertiesConstants
 *
 * @author 张麒 2017/5/8.
 * @version Description:
 */
public class PropertiesConstants {

    // Property File Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * property：静态资源地址
     */
    public final static String PROPERTY_STATIC_RESOURCE = "website.static.resources";
    /**
     * property：单点登录 - 登录服务
     */
    public final static String PROPERTY_SSO_LOGIN_URL = "sso.login.url";
    /**
     * property：单点登录 - 验证服务
     */
    public final static String PROPERTY_SSO_CHECK_URL = "sso.check.url";
    /**
     * property：单点登录 - 登出服务
     */
    public final static String PROPERTY_SSO_LOGOUT_URL = "sso.logout.url";
    /**
     * property：上传文件-存放生成的文件地址
     */
    public final static String MULTIPART_LOCATION = "spring.http.multipart.location";
    /**
     * property：上传文件-允许上传的单个文件最大值。默认值为 -1，表示没有限制
     */
    public final static String MULTIPART_MAX_FILE_SIZE = "spring.http.multipart.max-file-size";
    /**
     * property：上传文件-针对该 multipart/form-data 上传文件的最大值，默认值为 -1，表示没有限制
     */
    public final static String MULTIPART_MAX_REQUEST_SIZE = "spring_http.multipart.max-request-size";
    /**
     * property：上传文件-当数据量大于该值时，内容将被写入文件
     */
    public final static String MULTIPART_FILE_SIZE_THRESHOLD = "spring.http.multipart.file-size-threshold";

}
