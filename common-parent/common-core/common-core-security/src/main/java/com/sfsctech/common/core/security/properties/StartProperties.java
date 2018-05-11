package com.sfsctech.common.core.security.properties;


import com.sfsctech.common.core.base.filter.FilterHandler;
import com.sfsctech.common.support.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class StartProperties
 *
 * @author 张麒 2017-11-7.
 * @version Description:
 */
@Component
public class StartProperties {

    @Autowired
    private SecurityProperties properties;

    public SecurityProperties getProperties() {
        return properties;
    }

    @Autowired
    public void attribute() {
        // CSRF防御Token保持方式
        // TODO
        if (StringUtil.isNotBlank(properties.getCsrf().getKeepPattern())) {
//            CommonConstants.CSRF_KEEP_PATTERN = properties.getCsrf().getKeepPattern();
        }
        // CSRF防御拦截排除 - 路径
        if (null != properties.getCsrf().getInterceptExcludePath())
            FilterHandler.INTERCEPT_EXCLUDES_PATTERNS.addAll(properties.getCsrf().getInterceptExcludePath());
    }
}
