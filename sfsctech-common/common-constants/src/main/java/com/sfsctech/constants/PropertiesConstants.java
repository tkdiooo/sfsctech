package com.sfsctech.constants;

/**
 * Class PropertiesConstants
 *
 * @author 张麒 2017/5/8.
 * @version Description:
 */
public class PropertiesConstants {

    // the server Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：server.context-path"
     */
    public final static String SERVER_CONTEXTPATH = "server.context-path";
    /**
     * Properties：spring.mvc.view.suffix"
     */
    public final static String SERVER_SUFFIX = "spring.mvc.view.suffix";
    /**
     * Properties：spring.mvc.static-path-pattern"
     */
    public final static String SERVER_STATIC_PATH_PATTERN = "spring.mvc.static-path-pattern";

    // the custom Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：website.support.static-resources"
     */
    public final static String WEBSITE_SUPPORT_STATIC_RESOURCE = "website.support.static-resources";
    /**
     * Properties：website.request.filter-exclude"
     */
    public final static String WEBSITE_REQUEST_FILTER_EXCLUDE = "website.request.filter-exclude";
    /**
     * Properties：website.request.csrf-exclude"
     */
    public final static String WEBSITE_REQUEST_CSRF_EXCLUDE = "website.request.csrf-exclude";
    /**
     * Properties：website.sso.login-url
     */
    public final static String WEBSITE_SSO_LOGIN_URL = "website.sso.login-url";
    /**
     * Properties：website.sso.check-url
     */
    public final static String WEBSITE_SSO_CHECK_URL = "website.sso.check-url";
    /**
     * Properties：website.sso.logout-url
     */
    public final static String WEBSITE_SSO_LOGOUT_URL = "website.sso.logout-url";
    /**
     * Properties：website.sso.register-url
     */
    public final static String WEBSITE_SSO_REGISTER_URL = "website.sso.register-url";
    /**
     * Properties：website.sso.forget-url
     */
    public final static String WEBSITE_SSO_FORGET_URL = "website.sso.forget-url";
    /**
     * Properties：website.service.authentication"
     */
    public final static String WEBSITE_SERVICE_AUTHENTICATION = "website.service.authentication";

    // spring.http.multipart Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：spring.http.multipart.location
     */
    public final static String MULTIPART_LOCATION = "spring.http.multipart.location";
    /**
     * Properties：spring.http.multipart.max-file-size
     */
    public final static String MULTIPART_MAX_FILE_SIZE = "spring.http.multipart.max-file-size";
    /**
     * Properties：spring.http.multipart.max-request-size
     */
    public final static String MULTIPART_MAX_REQUEST_SIZE = "spring.http.multipart.max-request-size";
    /**
     * Properties：spring.http.multipart.file-size-threshold
     */
    public final static String MULTIPART_FILE_SIZE_THRESHOLD = "spring.http.multipart.file-size-threshold";

    // dubbo Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * Properties：dubbo.application.name
     */
    public final static String DUBBO_APPLICATION_NAME = "dubbo.application.name";
    /**
     * Properties：dubbo.application.logger
     */
    public final static String DUBBO_APPLICATION_LOGGER = "dubbo.application.logger";
    /**
     * Properties：dubbo.registry.address
     */
    public final static String DUBBO_REGISTRY_ADDRESS = "dubbo.registry.address";
    /**
     * Properties：dubbo.registry.check
     */
    public final static String DUBBO_REGISTRY_CHECK = "dubbo.registry.check";
    /**
     * Properties：dubbo.registry.register
     */
    public final static String DUBBO_REGISTRY_REGISTER = "dubbo.registry.register";
    /**
     * Properties：dubbo.registry.subscribe
     */
    public final static String DUBBO_REGISTRY_SUBSCRIBE = "dubbo.registry.subscribe";
    /**
     * Properties：dubbo.registry.timeout
     */
    public final static String DUBBO_REGISTRY_TIMEOUT = "dubbo.registry.timeout";
    /**
     * Properties：dubbo.protocol.name
     */
    public final static String DUBBO_PROTOCOL_NAME = "dubbo.protocol.name";
    /**
     * Properties：dubbo.protocol.port
     */
    public final static String DUBBO_PROTOCOL_PORT = "dubbo.protocol.port";
    /**
     * Properties：dubbo.protocol.kryo
     */
    public final static String DUBBO_PROTOCOL_KRYO = "dubbo.protocol.kryo";
}
