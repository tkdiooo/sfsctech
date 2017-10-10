package com.sfsctech.auth.util;

import com.sfsctech.auth.constants.AuthConstants;
import com.sfsctech.base.session.UserAuthData;

/**
 * Class CacheUtil
 *
 * @author 张麒 2017/10/10.
 * @version Description:
 */
public class CacheUtil {

    public static final String CACHE_API_KEY = "%!##@$%|$#$%(^)$}$*{^*+%";

    /**
     * 根据用户鉴权数据生成token缓存的key
     */
    public static String getTokenCacheKey(final UserAuthData authData) {
        return AuthConstants.UAMS_USER_TOKEN + AuthConstants.SPLIT_FLAG + authData.getSessionID() + AuthConstants.SPLIT_FLAG + authData.getAccount();
    }

    /**
     * 据用户鉴权数据生成salt缓存的key
     */
    public static String getSaltCacheKey(final UserAuthData authData) {
        return getTokenCacheKey(authData) + AuthConstants.SPLIT_FLAG + CACHE_API_KEY;
    }
}
