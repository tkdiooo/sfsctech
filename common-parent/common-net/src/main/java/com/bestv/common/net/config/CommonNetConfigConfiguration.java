//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.net.config;

import com.bestv.common.net.constants.CommonNetConstants;
import com.bestv.common.net.domain.CommonNetConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonNetConfigConfiguration {
    public CommonNetConfigConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({CommonNetConfig.class})
    public CommonNetConfig getCommonNetConfig() {
        CommonNetConfig commonNetConfig = new CommonNetConfig();
        commonNetConfig.setConnectionRequestTimeout(CommonNetConstants.DEFAULT_CONNECTION_REQUEST_TIMEOUT);
        commonNetConfig.setConnectionTimeout(CommonNetConstants.DEFAULT_CONNECTION_TIMEOUT);
        commonNetConfig.setReadTimeout(CommonNetConstants.DEFAULT_READ_TIMEOUT);
        return commonNetConfig;
    }
}
