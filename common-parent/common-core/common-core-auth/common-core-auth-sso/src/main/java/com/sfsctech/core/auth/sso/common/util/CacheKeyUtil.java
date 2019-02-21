package com.sfsctech.core.auth.sso.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.support.common.util.HexUtil;
import com.sfsctech.core.auth.sso.common.constants.SSOConstants;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * Class CacheUtil
 *
 * @author 张麒 2017/10/10.
 * @version Description:
 */
public class CacheKeyUtil {

    private static final String CACHE_API_KEY = "%!##@$%|$#$%(^)$}$*{^*+%";

    /**
     * 据用户鉴权数据生成salt缓存的key
     *
     * @return Salt_CacheKey
     */
    public static String getSaltCacheKey() {
        return HexUtil.getEncryptKey() + LabelConstants.DOUBLE_POUND + CACHE_API_KEY;
    }

    /**
     * 获取用户鉴权数据
     *
     * @param claims Jwt Claims
     * @return UserAuthData
     */
    @SuppressWarnings("unchecked")
    public static <T> T getUserAuthData(Claims claims, Class<T> cls) {
        JSONObject jo = new JSONObject();
        jo.putAll((Map) claims.get(SSOConstants.JWT_USER_AUTH_INFO));
        return JSONObject.parseObject(jo.toJSONString(), cls);
    }

    /**
     * 获取用户权限数据
     *
     * @param claims Jwt Claims
     * @return UserAuthData
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getUserPermitData(Claims claims) {
        return (Map) claims.get(SSOConstants.JWT_PERMIT_ATTRIBUTE);
    }
}
