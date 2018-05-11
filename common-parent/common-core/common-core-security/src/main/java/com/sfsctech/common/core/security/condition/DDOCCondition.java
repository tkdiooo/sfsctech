package com.sfsctech.common.core.security.condition;

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
        Boolean bool = context.getEnvironment().getProperty("website.security.ddoc.open", Boolean.class);
        return null != bool && bool;
    }
}
