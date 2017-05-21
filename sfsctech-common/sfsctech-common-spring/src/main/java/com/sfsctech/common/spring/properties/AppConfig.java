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
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.STATIC_RESOURCE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
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

    // dubbo 属性
    //-------------------------------------------------------------------------------------------
    /**
     * dubbo - 服务类所在的包路径，多个包名可以使用英文逗号分隔
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_ANNOTATION_PACKAGE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_ANNOTATION_PACKAGE;
    /**
     * dubbo - 提供方应用名称信息
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_APPLICATION_NAME + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_APPLICATION_NAME;
    /**
     * dubbo - 日志适配
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_APPLICATION_LOGGER + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_APPLICATION_LOGGER;
    /**
     * dubbo - zookeeper注册中心服务地址
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_ADDRESS + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_REGISTRY_ADDRESS;
    /**
     * dubbo - 注册中心不存在时，是否报错
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_CHECK + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean DUBBO_REGISTRY_CHECK;
    /**
     * dubbo - 是否向此注册中心注册服务，如果设为false，将只订阅，不注册
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_REGISTER + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean DUBBO_REGISTRY_REGISTRY;
    /**
     * dubbo - 是否向此注册中心订阅服务，如果设为false，将只注册，不订阅
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_SUBSCRIBE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean DUBBO_REGISTRY_SUBSCRIBE;
    /**
     * dubbo - 注册中心请求超时时间(毫秒)
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_TIMEOUT + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Integer DUBBO_REGISTRY_TIMEOUT;
    /**
     * dubbo - 通信协议，默认dubbo
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_NAME + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_PROTOCOL_NAME;
    /**
     * dubbo - 协议端口，默认20880
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_PORT + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Integer DUBBO_PROTOCOL_PORT;
    /**
     * dubbo - 序列化实现
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_SERIALIZATION + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_PROTOCOL_SERIALIZATION;
    /**
     * dubbo - SerializationOptimizer接口实现，添加需要序列化的类
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_OPTIMIZER + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String DUBBO_PROTOCOL_OPTIMIZER;


}
