package com.sfsctech.dubbo.sso.filter;

import com.sfsctech.core.auth.sso.filter.BaseSSOFilter;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.auth.sso.util.CacheKeyUtil;
import com.sfsctech.core.auth.sso.util.JwtUtil;
import com.sfsctech.core.auth.sso.util.SSOUtil;
import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import com.sfsctech.core.base.session.UserAuthData;
import com.sfsctech.dubbo.sso.util.SingletonUtil;
import com.sfsctech.support.common.security.EncrypterTool;
import io.jsonwebtoken.Claims;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SSOFilter extends BaseSSOFilter {

    @Override
    protected RpcResult<JwtToken> check(JwtToken jt, SSOProperties.AuthWay authWay) {
        if (authWay.equals(SSOProperties.AuthWay.Complex)) {
            return SingletonUtil.getVerifyService().complexVerify(jt);
        } else if (authWay.equals(SSOProperties.AuthWay.Simple)) {
            return SingletonUtil.getVerifyService().simpleVerify(jt);
        } else if (authWay.equals(SSOProperties.AuthWay.Local)) {
            return localVerify(jt);
        }
        RpcResult<JwtToken> result = new RpcResult<>();
        result.setSuccess(false);
        result.setMessage("校验规则无法匹配");
        return result;
    }

    private RpcResult<JwtToken> localVerify(JwtToken jt) {
        RpcResult<JwtToken> result = SSOUtil.generalVerify(jt, logger);
        if (!result.isSuccess()) {
            return result;
        }// 获取salt_CacheKey
        String salt_CacheKey = result.getAttachs().get("salt_CacheKey").toString();
        // 会话保持剩余时间（秒）
        long loginedTimeStamp = SingletonUtil.getCacheFactory().getCacheClient().ttl(salt_CacheKey + LabelConstants.POUND + result.getResult().getSalt());
        // 如果离超时间还有一半左右，重新生成Jwt
        if (SingletonUtil.getJwtProperties().getExpiration() > 0 && (loginedTimeStamp <= (SingletonUtil.getJwtProperties().getExpiration() / 2))) {
            return SingletonUtil.getVerifyService().complexVerify(jt);
        }
        return result;
    }
}
