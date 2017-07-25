package com.sfsctech.security.annotation;

import com.sfsctech.constants.SecurityConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class SOAExistsCondition
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
public class SOAExistsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return SecurityConstants.SERVER_SOA;
    }
}
