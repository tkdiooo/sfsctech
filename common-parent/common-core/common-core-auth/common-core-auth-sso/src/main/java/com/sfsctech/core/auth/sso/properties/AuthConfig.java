package com.sfsctech.core.auth.sso.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class AuthProperties
 *
 * @author 张麒 2017-12-4.
 * @version Description:
 */
@Component
@PropertySource("classpath:/auth.properties")
@ConfigurationProperties(prefix = "session")
public class AuthConfig {

    @Autowired
    private JwtProperties config;

    private Integer expiration;

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        if (null != config.getExpiration() && null == this.expiration) {
            this.expiration = config.getExpiration().intValue();
        } else if (null == this.expiration) {
            this.expiration = expiration;
        }
    }
}
