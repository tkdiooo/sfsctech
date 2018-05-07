//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.util;

import com.bestv.common.config.CommonConfig;
import com.bestv.common.config.ConfigKeyEnum;
import com.bestv.common.constant.Constants;
import com.bestv.common.lang.enums.BaseEnum;
import com.bestv.common.lang.ex.CommonErrorCode;
import com.bestv.common.lang.ex.CommonException;
import com.bestv.common.lang.ex.GenericException;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AssertUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssertUtil.class);
    private static Constructor<? extends CommonException> constructor;
    private static final Integer ERROR_STACK_DEPTH = 5;

    public AssertUtil() {
    }

    public static void isTrue(boolean value, CommonErrorCode commonErrorCode, Object... messages) {
        if (!value) {
            String extraMessage;
            if (messages.length == 0) {
                extraMessage = null;
            } else if (messages.length == 1) {
                if (messages[0] == null) {
                    extraMessage = null;
                } else {
                    extraMessage = messages[0].toString();
                }
            } else {
                String messagePattern = messages[0].toString();
                Object[] messageParams = new Object[messages.length - 1];

                for(int paramIndex = 0; paramIndex < messageParams.length; ++paramIndex) {
                    messageParams[paramIndex] = messages[paramIndex + 1];
                }

                extraMessage = MessageFormat.format(messagePattern, messageParams);
            }

            CommonException exception;
            try {
                exception = (CommonException)constructor.newInstance(commonErrorCode, extraMessage);
                regularStackTraceElements(exception);
            } catch (Exception var7) {
                LOGGER.error("创建异常对象错误", var7);
                throw new RuntimeException(var7);
            }

            throw (RuntimeException)exception;
        }
    }

    public static void isBlank(String value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(StringUtils.isBlank(value), commonErrorCode, messages);
    }

    public static void isNotBlank(String value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(StringUtils.isNotBlank(value), commonErrorCode, messages);
    }

    public static void isEmpty(Collection value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(CollectionUtils.isEmpty(value), commonErrorCode, messages);
    }

    public static void isNotEmpty(Collection value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(CollectionUtils.isNotEmpty(value), commonErrorCode, messages);
    }

    public static void isNotNull(Object value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(value != null, commonErrorCode, messages);
    }

    public static void isNull(Object value, CommonErrorCode commonErrorCode, Object... messages) {
        isTrue(value == null, commonErrorCode, messages);
    }

    public static void isEnumCodeValid(String enumCode, Class<? extends BaseEnum> enumClass, CommonErrorCode commonErrorCode, Object... messages) {
        isNotNull(CommonUtil.getByCode(enumCode, enumClass), commonErrorCode, messages);
    }

    public static void sizeEq(Collection collection, int size, CommonErrorCode commonErrorCode, Object... messages) {
        isNotNull(collection, commonErrorCode, "集合对象为null");
        isTrue(collection.size() == size, commonErrorCode, messages);
    }

    private static void regularStackTraceElements(CommonException exception) {
        StackTraceElement[] sourceStackTraceElements = exception.getStackTrace();
        StackTraceElement[] targetStackTraceElements = new StackTraceElement[sourceStackTraceElements.length - ERROR_STACK_DEPTH];

        for(int targetElementIndex = 0; targetElementIndex < targetStackTraceElements.length; ++targetElementIndex) {
            targetStackTraceElements[targetElementIndex] = sourceStackTraceElements[targetElementIndex + ERROR_STACK_DEPTH];
        }

        exception.setStackTrace(targetStackTraceElements);
    }

    static {
        try {
            constructor = GenericException.class.getConstructor(CommonErrorCode.class, String.class);
            LOGGER.info("初始化默认异常构造器成功.");
        } catch (NoSuchMethodException var9) {
            LOGGER.error("默认异常构造器初始化失败...", var9);
            throw Constants.UNKNOWN_ERROR;
        }

        String exceptionClassName = null;

        try {
            exceptionClassName = (String)CommonConfig.getProperty(ConfigKeyEnum.ASSERTUTIL_EXCEPTION_CLASS_NAME);
            Class exceptionClass;
            if (StringUtils.isNotBlank(exceptionClassName)) {
                exceptionClass = Class.forName(exceptionClassName);
                LOGGER.info("载入自定义异常类: {} 成功", exceptionClassName);
            } else {
                LOGGER.error("载入自定义异常类名失败, 将使用默认异常类", exceptionClassName);
                exceptionClass = GenericException.class;
            }

            Constructor[] exceptionConstructors = exceptionClass.getConstructors();
            boolean foundConstructor = false;
            Constructor[] var4 = exceptionConstructors;
            int var5 = exceptionConstructors.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Constructor exceptionConstructor = var4[var6];
                Class[] parameterTypes = exceptionConstructor.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 2 && CommonErrorCode.class.isAssignableFrom(parameterTypes[0]) && String.class == parameterTypes[1]) {
                    constructor = exceptionConstructor;
                    foundConstructor = true;
                }
            }

            if (foundConstructor) {
                LOGGER.info("初始化异常构造器成功.");
            } else {
                LOGGER.warn("初始化异常构造器失败, 将使用默认异常构造器");
            }
        } catch (ClassNotFoundException var10) {
            LOGGER.error("没有找到异常定义类: {}, 将使用默认异常定义类 \n异常信息: {}.", exceptionClassName, var10.getMessage());
        }

    }
}
