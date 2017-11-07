package com.sfsctech.security.condition;

import com.sfsctech.constants.PropertiesConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class XSSCondition
 *
 * @author 张麒 2017-11-7.
 * @version Description:
 */
public class XSSCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Boolean optimize = context.getEnvironment().getProperty(PropertiesConstants.WEBSITE_SECURITY_OPEN_XSS, Boolean.class);
        return null != optimize;
    }
}
