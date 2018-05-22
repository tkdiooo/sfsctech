package com.sfsctech.core.spring.util;

import com.sfsctech.core.base.enums.BaseEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 读取spring ResourceBundleMessageSource配置文件
 *
 * @author 张麒 2016/4/8.
 * @version Description:
 */
public class ResourceUtil {

    private static MessageSource messageSource = SpringContextUtil.getBean(MessageSource.class);

    /**
     * 获取messageSource
     *
     * @return
     */
    public static MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * 获取messages
     *
     * @param code   对应messages配置的key.
     * @param params 参数.
     * @return
     */

    public static String getMessage(String code, String... params) {
        return getMessage(code, "", params);
    }

    /**
     * 获取messages
     *
     * @param code           对应messages配置的key.
     * @param defaultMessage 没有匹配数据时的默认值.
     * @param params         参数.
     * @return
     */
    public static String getMessage(String code, String defaultMessage, String... params) {
        //这里使用比较方便的方法，不依赖request.
        return getMessageSource().getMessage(code, params, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * 获取messages
     *
     * @param code   对应messages配置的key.
     * @param locale Locale
     * @param params 参数
     * @return
     */
    public static String getMessage(String code, Locale locale, String... params) {
        return getMessageSource().getMessage(code, params, locale == null ? LocaleContextHolder.getLocale() : locale);
    }

    /**
     * 根据枚举接口获取messages
     *
     * @param eu
     * @param params
     * @param <K>
     * @return
     */
    public static <K> String getMessage(BaseEnum<K, String> eu, String... params) {
        return getMessage(eu.getDescription(), params);
    }
}
