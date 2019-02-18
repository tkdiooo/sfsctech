package com.sfsctech.dubbo.sso.filter;

import com.sfsctech.core.auth.sso.filter.BaseSSOFilter;
import com.sfsctech.core.auth.sso.inf.SSOAuthorizationInterface;
import com.sfsctech.dubbo.sso.util.SingletonUtil;

/**
 * Class SSOFilter
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public abstract class DubboSSOFilter extends BaseSSOFilter {

    @Override
    protected SSOAuthorizationInterface getcheck() {
        return new SingletonUtil();
    }


}
