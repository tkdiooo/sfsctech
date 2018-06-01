package com.sfsctech.core.base.constants;

/**
 * Class CommonConstants
 *
 * @author 张麒 2016/5/25.
 * @version Description:
 */
public class CommonConstants {

    // Web
    //-------------------------------------------------------------------------------------------
    /**
     * Ajax请求表头
     */
    public static final String AJAX_ACCEPT_CONTENT_TYPE[] = {"Accept", "text/html;type=ajax"};
    public static final String AJAX_HEADER_CONTENT_TYPE[] = {"x-requested-with", "XMLHttpRequest"};
    /**
     * Ajax请求时间戳
     */
    public static final String AJAX_TIME_FRESH = "ajaxTimeFresh";

    public static final String VIEW_404 = "error/404";
    public static final String VIEW_500 = "error/500";

    // Common Attribute
    //-------------------------------------------------------------------------------------------
    public static final String SUCCESS = "success";
    public static final String MESSAGES = "messages";
    public static final String MESSAGES_DETAILS = "messages_details";


    // Startup Parameters Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * 国际化文件路径
     */
    public static final String[] RESOURCES_I18N_PATH = {"i18n/defaultMessages", "i18n/messages"};

    // Front UI Attribute
    //-------------------------------------------------------------------------------------------
    /**
     * 静态资源地址
     */
    public static final String STATIC_RESOURCE_ATTRIBUTE = "static_resource";

    /**
     * 项目ContextPath
     */
    public static final String CONTEXT_PATH_ATTRIBUTE = "context_path";

    /**
     * 网站域名
     */
    public static final String WEBSITE_DOMAIN_ATTRIBUTE = "website_domain";

    // Logback Setting Attribute
    //-------------------------------------------------------------------------------------------

}
