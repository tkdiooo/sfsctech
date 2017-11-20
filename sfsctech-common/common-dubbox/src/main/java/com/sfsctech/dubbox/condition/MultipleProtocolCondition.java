package com.sfsctech.dubbox.condition;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.PropertiesConstants;
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
        String single = context.getEnvironment().getProperty(PropertiesConstants.DUBBO_PROTOCOL_SINGLE);
        return StringUtil.isNotBlank(single) && !Boolean.valueOf(single);
    }
}
