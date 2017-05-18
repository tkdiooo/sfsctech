package com.sfsctech.common.constants;

/**
 * Class PropertiesConstants
 *
 * @author 张麒 2017/5/8.
 * @version Description:
 */
public class PropertiesConstants {

    // the custom Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：静态资源地址
     */
    public final static String STATIC_RESOURCE = "website.static.resources";
    /**
     * Properties：单点登录 - 登录服务
     */
    public final static String SSO_LOGIN_URL = "sso.login.url";
    /**
     * Properties：单点登录 - 验证服务
     */
    public final static String SSO_CHECK_URL = "sso.check.url";
    /**
     * Properties：单点登录 - 登出服务
     */
    public final static String SSO_LOGOUT_URL = "sso.logout.url";

    // spring.http.multipart Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：上传文件-存放生成的文件地址
     */
    public final static String MULTIPART_LOCATION = "spring.http.multipart.location";
    /**
     * Properties：上传文件-允许上传的单个文件最大值。默认值为 -1，表示没有限制
     */
    public final static String MULTIPART_MAX_FILE_SIZE = "spring.http.multipart.max-file-size";
    /**
     * Properties：上传文件-针对该 multipart/form-data 上传文件的最大值，默认值为 -1，表示没有限制
     */
    public final static String MULTIPART_MAX_REQUEST_SIZE = "spring.http.multipart.max-request-size";
    /**
     * Properties：上传文件-当数据量大于该值时，内容将被写入文件
     */
    public final static String MULTIPART_FILE_SIZE_THRESHOLD = "spring.http.multipart.file-size-threshold";

    // dubbo Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：dubbo application name
     */
    public final static String DUBBO_APPLICATION_NAME = "dubbo.application.name";
    /**
     * Properties：dubbo application logger
     */
    public final static String DUBBO_APPLICATION_LOGGER = "dubbo.application.logger";
    /**
     * Properties：dubbo registry address
     */
    public final static String DUBBO_REGISTRY_ADDRESS = "dubbo.registry.address";
    /**
     * Properties：dubbo registry check
     */
    public final static String DUBBO_REGISTRY_CHECK = "dubbo.registry.check";
    /**
     * Properties：dubbo registry register
     */
    public final static String DUBBO_REGISTRY_REGISTER = "dubbo.registry.register";
    /**
     * Properties：dubbo registry subscribe
     */
    public final static String DUBBO_REGISTRY_SUBSCRIBE = "dubbo.registry.subscribe";
    /**
     * Properties：dubbo registry timeout
     */
    public final static String DUBBO_REGISTRY_TIMEOUT = "dubbo.registry.timeout";

}
