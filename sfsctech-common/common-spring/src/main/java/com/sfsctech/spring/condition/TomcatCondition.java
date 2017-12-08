package com.sfsctech.spring.condition;

import com.sfsctech.constants.PropertiesConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class TomcatCondition
 *
 * @author 张麒 2017/9/14.
 * @version Description:
 */
public class TomcatCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Boolean bool = context.getEnvironment().getProperty(PropertiesConstants.SERVER_TOMCAT_OPTIMIZE, Boolean.class);
        return null != bool && bool;
    }
}
