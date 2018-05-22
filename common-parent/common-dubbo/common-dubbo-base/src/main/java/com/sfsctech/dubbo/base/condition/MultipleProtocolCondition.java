package com.sfsctech.dubbo.base.condition;

import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.dubbo.base.constants.DubboConstants;
import com.sfsctech.dubbo.base.properties.DubboProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Class MultipleProtocolCondition
 *
 * @author 张麒 2017/10/11.
 * @version Description:
 */
public class MultipleProtocolCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String config = context.getEnvironment().getProperty(DubboConstants.DUBBO_PROTOCOL_CONFIG);
        return StringUtil.isNotBlank(config) && DubboProperties.Config.Multiple.name().equals(config);
    }
}
