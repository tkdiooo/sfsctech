//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.config;

import com.bestv.common.config.convertor.DefaultPropertyTypeConvertor;
import com.bestv.common.config.convertor.IntegerPropertyTypeConvertor;
import com.bestv.common.config.convertor.PropertyTypeConvertor;
import com.bestv.common.config.ex.PropertyNotExistsException;
import com.bestv.common.config.ex.PropertyTypeConvertorNotExistsException;
import com.bestv.common.lang.ex.GenericException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);
    private static final Properties PROPERTIES_SOURCE = new Properties();
    private static final String CONFIG_PATH = "config/common.properties";
    private static final Map<Class, PropertyTypeConvertor> PROPERTY_TYPE_CONVERTOR_MAP = new ConcurrentHashMap();

    public CommonConfig() {
    }

    public static <T> T getProperty(ConfigKeyEnum configKeyEnum) throws GenericException {
        String value = PROPERTIES_SOURCE.getProperty(configKeyEnum.getCode());
        if (!configKeyEnum.isAllowNull() && value == null) {
            LOGGER.error("{} 配置不可为空, 请配置之.", configKeyEnum.getCode());
            throw new PropertyNotExistsException(configKeyEnum);
        } else {
            return convert(value, configKeyEnum.getPropertyType());
        }
    }

    private static <T> T convert(String value, Class<T> clazz) {
        PropertyTypeConvertor<T> propertyTypeConvertor = getPropertyTypeConvertor(clazz);
        if (propertyTypeConvertor == null) {
            throw new PropertyTypeConvertorNotExistsException(clazz);
        } else {
            return propertyTypeConvertor.convert(value);
        }
    }

    private static <T> PropertyTypeConvertor<T> getPropertyTypeConvertor(Class<T> clazz) {
        return (PropertyTypeConvertor)PROPERTY_TYPE_CONVERTOR_MAP.get(clazz);
    }

    private static void loadPropertyTypeConvertor() {
        PROPERTY_TYPE_CONVERTOR_MAP.put(String.class, new DefaultPropertyTypeConvertor());
        PROPERTY_TYPE_CONVERTOR_MAP.put(Integer.class, new IntegerPropertyTypeConvertor());
    }

    private static void loadProperties() {
        try {
            LOGGER.info("开始载入配置文件: {}", "config/common.properties");
            PROPERTIES_SOURCE.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/common.properties"));
            LOGGER.info("载入配置文件: {} 成功", "config/common.properties");
        } catch (Exception var1) {
            LOGGER.error("载入配置文件: {} 失败!", "config/common.properties");
        }

    }

    static {
        loadProperties();
        loadPropertyTypeConvertor();
    }
}
