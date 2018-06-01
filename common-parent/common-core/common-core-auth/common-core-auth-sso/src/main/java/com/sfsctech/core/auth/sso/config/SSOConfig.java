package com.sfsctech.core.auth.sso.config;

import com.sfsctech.core.auth.sso.properties.JwtProperties;
import com.sfsctech.core.auth.sso.properties.SSOProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Class AuthProperties
 *
 * @author 张麒 2017-12-4.
 * @version Description:
 */
@Configuration
@Import({SSOProperties.class, JwtProperties.class})
@PropertySource("classpath:/auth.properties")
@ConfigurationProperties(prefix = "session")
public class SSOConfig {

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
