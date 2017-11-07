package com.sfsctech.security.condition;

import com.sfsctech.constants.PropertiesConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class DdocCondition
 *
 * @author 张麒 2017-11-7.
 * @version Description:
 */
public class DDOCCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Boolean optimize = context.getEnvironment().getProperty(PropertiesConstants.WEBSITE_SECURITY_OPEN_DDOC, Boolean.class);
        return null != optimize;
    }
}
