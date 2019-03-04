package com.sfsctech.cloud.sso.filter;

import com.sfsctech.cloud.sso.util.SingletonUtil;
import com.sfsctech.core.auth.sso.filter.BaseSSOFilter;
import com.sfsctech.core.auth.sso.inf.SSOCheckInterface;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import com.sfsctech.core.base.domain.result.RpcResult;
import com.sfsctech.core.base.jwt.JwtToken;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
// TODO 重新实现
public class SSOFilter {//extends BaseSSOFilter {

//    @Override
//    protected RpcResult<JwtToken> check(JwtToken jt, SSOProperties.AuthWay authWay) {
//        if (authWay.equals(SSOProperties.AuthWay.Complex)) {
//            return SingletonUtil.getVerifyService().complexVerify(jt);
//        } else {
//            return SingletonUtil.getVerifyService().simpleVerify(jt);
//        }
//    }

//    @Override
    protected SSOCheckInterface getcheck() {
        return null;
    }

//    @Override
    protected void generateSesssion(Claims claims, HttpServletRequest request) {

    }

//    @Override
    protected RpcResult<JwtToken> localVerify(JwtToken jt) {
        return null;
    }
}
