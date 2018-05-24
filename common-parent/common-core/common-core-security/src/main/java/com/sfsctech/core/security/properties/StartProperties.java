package com.sfsctech.core.security.properties;

import com.sfsctech.core.base.filter.FilterHandler;
import com.sfsctech.support.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Class StartProperties
 *
 * @author 张麒 2017-11-7.
 * @version Description:
 */
@Component
@Import(SecurityProperties.class)
public class StartProperties {

    @Autowired
    private SecurityProperties properties;

    public SecurityProperties getProperties() {
        return properties;
    }

    @Autowired
    public void attribute() {
        // CSRF 拦截排除路径
        if (null != properties.getCsrf() && null != properties.getCsrf().getInterceptExcludePath())
            FilterHandler.INTERCEPT_EXCLUDES_PATTERNS.addAll(properties.getCsrf().getInterceptExcludePath());
    }
}
