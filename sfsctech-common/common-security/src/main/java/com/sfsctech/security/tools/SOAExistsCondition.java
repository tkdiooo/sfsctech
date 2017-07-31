package com.sfsctech.security.tools;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.PropertiesConstants;
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
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String serverSOA = context.getEnvironment().getProperty(PropertiesConstants.SERVER_SOA);
        if (StringUtil.isNotBlank(serverSOA)) {
            return Boolean.valueOf(serverSOA);
        }
        return false;
    }
}
