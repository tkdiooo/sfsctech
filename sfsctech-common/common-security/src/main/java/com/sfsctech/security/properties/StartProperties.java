package com.sfsctech.security.properties;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.CommonConstants;
import com.sfsctech.constants.ExcludesConstants;
import com.sfsctech.constants.LabelConstants;
import com.sfsctech.constants.PropertiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        if (StringUtil.isNotBlank(properties.getCsrf().getKeepPattern())) {
            CommonConstants.CSRF_KEEP_PATTERN = properties.getCsrf().getKeepPattern();
        }
        // CSRF防御拦截排除 - 路径
        if (null != properties.getCsrf().getInterceptExcludePath())
            ExcludesConstants.INTERCEPT_EXCLUDES_PATTERNS.addAll(properties.getCsrf().getInterceptExcludePath());
    }
}
