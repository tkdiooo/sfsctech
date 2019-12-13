package com.sfsctech.support.saml.properties;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class SamlConfig
 *
 * @author 张麒 2019-12-4.
 * @version Description:
 */
@Component
@ConfigurationProperties(
        prefix = "saml"
)
public class SamlConfig {

    private Map<String, SpConfig> spConfig = Maps.newHashMap();

    public Map<String, SpConfig> getSpConfig() {
        return spConfig;
    }

    public void setSpConfig(Map<String, SpConfig> spConfig) {
        this.spConfig = spConfig;
    }
}
