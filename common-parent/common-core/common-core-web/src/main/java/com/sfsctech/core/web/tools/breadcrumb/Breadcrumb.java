package com.sfsctech.core.web.tools.breadcrumb;

import com.google.common.collect.Lists;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.core.exception.ex.BizException;
import com.sfsctech.core.spring.util.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Breadcrumb
 *
 * @author 张麒 2017-11-8.
 * @version Description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Breadcrumb {

    private String text;
    private String url;
    private Map<String, String> params = new HashMap<>();
    private String cls;

    @SuppressWarnings({"unchecked"})
    private static CacheFactory<RedisProxy<String, Object>> factory = SpringContextUtil.getBean(CacheFactory.class);

    public static List<Breadcrumb> buildBreadcrumb(Breadcrumb breadcrumb, String key, int position) {
        List<Breadcrumb> list;
        // 获取当前节点缓存数据不为空
        if (null != (list = factory.getList(key))) {
            return list;
        }
        // 当前节点为顶级节点
        if (position == 0) {
            list = Lists.newArrayList(breadcrumb);
        } else {
            // 拆分编号-当前节点父级编号N位
            String praent_key = key.substring(0, key.length() - position);
            // 获取父级节点，如果父级节点为空，则抛出异常
            if (null == (list = factory.getList(praent_key))) {
                throw new BizException("父级节点{}缓存数据为空", praent_key);
            }
            list.add(breadcrumb);
        }
        factory.getCacheClient().put(key, list);
        return list;
    }

    public void addParams(String key, String value) {
        this.params.put(key, value);
    }
}
