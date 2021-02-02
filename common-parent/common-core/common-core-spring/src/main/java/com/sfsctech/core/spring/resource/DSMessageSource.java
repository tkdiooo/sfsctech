package com.sfsctech.core.spring.resource;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Class MyMessageSource
 *
 * @author 张麒 2020-12-2.
 * @version Description:
 */
@Slf4j
public class DSMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    // 这个是用来缓存数据库中获取到的配置的 数据库配置更改的时候可以调用reload方法重新加载
    private static final Map<String, Map<String, String>> LOCAL_CACHE = Maps.newConcurrentMap();

    @Autowired
    private HttpServletRequest request;

    /**
     * 重新将数据库中的国际化配置加载
     */
    public void reload(Map<String, Map<String, String>> message) {
        LOCAL_CACHE.clear();
        LOCAL_CACHE.putAll(message);
    }

    /**
     * 从缓存中取出国际化配置对应的数据 或者从父级获取
     *
     * @param code
     * @param locale 可以为null, 表示从当前HttpServletRequest中获取语言
     * @return
     */
    public String getSourceFromCache(String code, Locale locale) {
        String language = locale == null ? RequestContextUtils.getLocale(request).getLanguage() : locale.getLanguage();
        // 获取缓存中对应语言的所有数据项
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            // 如果对应语言中能匹配到数据项，那么直接返回
            return props.get(code);
        } else {
            // 如果对应语言中不能匹配到数据项，从上级获取返回
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(code, null, locale);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            // 如果上级也没有找到，那么返回请求键值
            return code;
        }
    }

    // 下面三个重写的方法是比较重要的
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        return new MessageFormat(getSourceFromCache(code, locale), locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
