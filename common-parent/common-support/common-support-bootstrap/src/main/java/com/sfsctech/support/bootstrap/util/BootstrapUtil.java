package com.sfsctech.support.bootstrap.util;

import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.base.enums.BaseEnum;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.spring.util.SpringContextUtil;
import com.sfsctech.support.common.util.BeanUtil;
import com.sfsctech.support.common.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class BootstrapUtil
 *
 * @author 张麒 2017-11-6.
 * @version Description:
 */
public class BootstrapUtil {

    private static String[] OPTIONS_CLASS = {"text-red", "text-green", "text-aqua", "text-yellow"};

    private static CacheFactory factory = SpringContextUtil.getBean(CacheFactory.class);

    /**
     * 匹配下拉列表options
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <K, V> List<Map<String, Object>> options(String cacheKey, BaseEnum<K, V>... enums) {
        List<Map<String, Object>> options;
        if (StringUtil.isNotBlank(cacheKey) && null != (options = (List<Map<String, Object>>) factory.getCacheClient().get(cacheKey))) {
            return options;
        }
        options = new ArrayList<>();
        for (BaseEnum<K, V> e : enums) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", e.getCode());
            map.put("text", e.getDescription());
            if (StringUtil.isNumeric(e.getCode().toString())) {
                map.put("class", OPTIONS_CLASS[Integer.valueOf(e.getCode().toString())]);
            }
            options.add(map);
        }
        factory.getCacheClient().putTimeOut(cacheKey, options, CacheConstants.Second.Minutes30.getContent());
        return options;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Map<String, Object>> options(String cacheKey, List<T> list, String valProperty, String textProperty) {
        List<Map<String, Object>> options;
        if (StringUtil.isNotBlank(cacheKey) && null != (options = (List<Map<String, Object>>) factory.getCacheClient().get(cacheKey))) {
            return options;
        }
        options = new ArrayList<>();
        for (T t : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", BeanUtil.getPropertyValue(t, valProperty));
            map.put("text", BeanUtil.getPropertyValue(t, textProperty));
            options.add(map);
        }
        factory.getCacheClient().putTimeOut(cacheKey, options, CacheConstants.Second.Minutes30.getContent());
        return options;
    }

}
