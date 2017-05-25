package com.sfsctech.common.dubbox.properties;

import com.sfsctech.common.constants.LabelConstants;
import com.sfsctech.common.constants.PropertiesConstants;
import com.sfsctech.common.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class DubboConfig
 *
 * @author 张麒 2017/5/21.
 * @version Description:
 */
@Component
public class DubboConfig {

    // dubbo 属性
    //-------------------------------------------------------------------------------------------
    /**
     * dubbo - 服务类所在的包路径，多个包名可以使用英文逗号分隔
     */
    private static String[] ANNOTATION_PACKAGE;

    /**
     * dubbo - 服务类所在的包路径，多个包名可以使用英文逗号分隔
     */
    public static void setAnnotationPackage(String... params) {
        ANNOTATION_PACKAGE = params;
    }

    /**
     * dubbo - 服务类所在的包路径，多个包名可以使用英文逗号分隔
     */
    public static String getAnnotationPackage() {
        return ArrayUtil.toString(ANNOTATION_PACKAGE, LabelConstants.COMMA);
    }

    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    private static String[] KRYO_SERIALIZE_PACKAGE;

    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    public static String getKryoSerializePackage() {
        return ArrayUtil.toString(KRYO_SERIALIZE_PACKAGE, LabelConstants.COMMA);
    }

    /**
     * dubbo - kryo 需要序列化的类所在包路径，多个包名可以使用英文逗号分隔
     */
    public static void setKryoSerializePackage(String... kryoSerializePackage) {
        KRYO_SERIALIZE_PACKAGE = kryoSerializePackage;
    }

    private static String[] FILTERS = {"-exception", "ExceptionHandler"};

    public static String getFILTERS() {
        return ArrayUtil.toString(FILTERS, LabelConstants.COMMA);
    }

    public static void setFILTERS(String[] FILTERS) {
        DubboConfig.FILTERS = FILTERS;
    }

    /**
     * dubbo - 提供方应用名称信息
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_APPLICATION_NAME + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String APPLICATION_NAME;
    /**
     * dubbo - 日志适配
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_APPLICATION_LOGGER + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String APPLICATION_LOGGER;
    /**
     * dubbo - zookeeper注册中心服务地址
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_ADDRESS + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String REGISTRY_ADDRESS;
    /**
     * dubbo - 注册中心不存在时，是否报错
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_CHECK + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean REGISTRY_CHECK;
    /**
     * dubbo - 是否向此注册中心注册服务，如果设为false，将只订阅，不注册
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_REGISTER + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean REGISTRY_REGISTRY;
    /**
     * dubbo - 是否向此注册中心订阅服务，如果设为false，将只注册，不订阅
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_SUBSCRIBE + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean REGISTRY_SUBSCRIBE;
    /**
     * dubbo - 注册中心请求超时时间(毫秒)
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_REGISTRY_TIMEOUT + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Integer REGISTRY_TIMEOUT;
    /**
     * dubbo - 通信协议，默认dubbo
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_NAME + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public String PROTOCOL_NAME;
    /**
     * dubbo - 协议端口，默认20880
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_PORT + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE)
    public Integer PROTOCOL_PORT;
    /**
     * dubbo - 是否使用kryo序列化，默认false
     */
    @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + PropertiesConstants.DUBBO_PROTOCOL_KRYO + LabelConstants.COLON + LabelConstants.FALSE + LabelConstants.CLOSE_CURLY_BRACE)
    public Boolean IS_EMPLOY_KRYO;
}
