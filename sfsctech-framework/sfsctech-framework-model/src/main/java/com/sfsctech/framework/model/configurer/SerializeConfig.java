package com.sfsctech.framework.model.configurer;

import com.sfsctech.constants.DubboConstants;
import org.springframework.stereotype.Component;

/**
 * Class InitConfig
 *
 * @author 张麒 2017/5/26.
 * @version Description:
 */
@Component
public class SerializeConfig {

    static {
        DubboConstants.addKryoSerializePackage("com.sfsctech.framework.model.dto");
    }
}
