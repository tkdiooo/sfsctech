package com.sfsctech.dubbox.condition;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.PropertiesConstants;
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
        String single = context.getEnvironment().getProperty(PropertiesConstants.DUBBO_PROTOCOL_SINGLE);
        return StringUtil.isBlank(single) || Boolean.valueOf(single);
    }
}
