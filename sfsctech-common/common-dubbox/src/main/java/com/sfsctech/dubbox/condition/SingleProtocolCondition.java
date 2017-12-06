package com.sfsctech.dubbox.condition;

import com.sfsctech.constants.PropertiesConstants;
import com.sfsctech.dubbox.properties.DubboProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class ProtocolCondition
 *
 * @author 张麒 2017/10/11.
 * @version Description:
 */
public class SingleProtocolCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String config = context.getEnvironment().getProperty(PropertiesConstants.DUBBO_PROTOCOL_CONFIG);
        return null != config && DubboProperties.Config.Single.name().equals(config);
    }
}
