package com.sfsctech.core.cache.condition;

import com.sfsctech.core.cache.properties.RedisProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class SingleProtocolCondition
 *
 * @author 张麒 2017-11-2.
 * @version Description:
 */
public class SingleProtocolCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String protocol = context.getEnvironment().getProperty("spring.redis.protocol");
        return RedisProperties.Protocol.Single.name().equals(protocol);
    }
}
