package com.sfsctech.security.condition;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.PropertiesConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class Simple
 *
 * @author 张麒 2017/8/28.
 * @version Description:
 */
public class SimpleAuthentication implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String authentication = context.getEnvironment().getProperty(PropertiesConstants.WEBSITE_SERVICE_AUTHENTICATION);
        return StringUtil.isNotBlank(authentication) && authentication.equals("simple");
    }
}