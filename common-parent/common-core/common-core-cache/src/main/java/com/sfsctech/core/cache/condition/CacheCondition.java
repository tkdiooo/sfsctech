package com.sfsctech.core.cache.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class CacheProtocolCondition
 *
 * @author 张麒 2017-11-3.
 * @version Description:
 */
public class CacheCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return null != context.getEnvironment().getProperty("spring.redis.protocol");
    }
}
