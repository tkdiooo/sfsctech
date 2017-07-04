package com.sfsctech.mybatis.dao.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sfsctech.cache.inf.ICacheFactory;
import com.sfsctech.cache.inf.ICacheService;
import com.sfsctech.common.util.BeanUtil;
import com.sfsctech.constants.LabelConstants;

/**
 * Class SqlMapClientTemplateCacheMonitor
 *
 * @author 张麒 2017/6/29.
 * @version Description:
 */
class MybatisCacheMonitor<Example> {

    private ICacheFactory<String, Object> cacheClient;

    final void setCacheClient(ICacheFactory<String, Object> cacheClient) {
        this.cacheClient = cacheClient;
    }

    final ICacheService<String, Object> getCacheClient() {
        return cacheClient.getCacheClient();
    }

    String getCacheKey(String namespace, String key) {
        return namespace + LabelConstants.UNDERLINE + key;
    }

    String getCacheKey(String namespace, Example example) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(example);
        JSONArray jsonArray = jsonObject.getJSONArray("oredCriteria").getJSONObject(0).getJSONArray("allCriteria");
        for (Object json : jsonArray) {
            JSONObject jo = (JSONObject) json;
            if (jo.getString("condition").startsWith("Guid")) {
                return getCacheKey(namespace, jo.getString("value"));
            }
        }
        return "";
    }

    void putTimeOut(String namespace, Object value, int timeOut) {
        try {
            String cacheKey = getCacheKey(namespace, BeanUtil.getProperty(value, "guid"));
            getCacheClient().putTimeOut(cacheKey, value, timeOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
