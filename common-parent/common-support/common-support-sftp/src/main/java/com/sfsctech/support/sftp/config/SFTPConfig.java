package com.sfsctech.support.sftp.config;

import com.sfsctech.core.base.constants.LabelConstants;
import com.sfsctech.support.sftp.factory.SFTPConnectionFactory;
import com.sfsctech.support.sftp.properties.SFTPProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Class SFTPConfig
 *
 * @author 张麒 2018-12-25.
 * @version Description:
 */
@Configuration
@Import({SFTPProperties.class})
public class SFTPConfig {

    @Autowired
    private SFTPProperties properties;

    @Bean("sftpConnectionFactory")
    public SFTPConnectionFactory SFTPConnectionFactory() {
        return new SFTPConnectionFactory(properties);
    }


    @Autowired
    public void attribute(
            @Value(LabelConstants.DOLLAR_AND_OPEN_CURLY_BRACE + "spring.application.name" + LabelConstants.COLON + LabelConstants.CLOSE_CURLY_BRACE) String appName) {
        this.properties.setAppName(appName);
    }
}
